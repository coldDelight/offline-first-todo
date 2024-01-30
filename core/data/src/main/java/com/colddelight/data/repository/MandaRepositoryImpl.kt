package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.WriteTask
import com.colddelight.data.model.WriteType
import com.colddelight.data.util.newUpdateTime
import com.colddelight.data.util.updatedTimeStamp
import com.colddelight.database.dao.MandaDao
import com.colddelight.database.model.MandaEntity
import com.colddelight.database.model.asEntity
import com.colddelight.database.model.asModel
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Manda
import com.colddelight.network.datasource.MandaDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaRepositoryImpl @Inject constructor(
    private val mandaDao: MandaDao,
    private val userDataSource: UserPreferencesDataSource,
    private val mandaDataSource: MandaDataSource,
    private val writeTask: WriteTask,
) : MandaRepository {

    override val isNewUser: Flow<Boolean> = userDataSource.isNewUser
    override suspend fun initManda() {
        val defaultMandaList = List(9) { MandaEntity(cnt = 0, false, updatedTimeStamp(), 0) }
        mandaDao.initManda(defaultMandaList)
        userDataSource.saveIsNewUser()
        writeTask.writeReq(WriteType.Manda)
    }

    override fun getAllManda(): Flow<List<Manda>> {
        return mandaDao.getAllManda().map { mandaEntity -> mandaEntity.map { it.asModel() } }
    }

    override suspend fun updateManda(manda: Manda) {
        mandaDao.updateManda(manda.asEntity().copy(updateTime = updatedTimeStamp(), isSync = false))
        writeTask.writeReq(WriteType.Manda)
    }

    override suspend fun deleteAllManda() {
        userDataSource.delIsNewUser()
        mandaDao.deleteAllManda()
    }

    override suspend fun write(): Boolean {
        return try {
            val toWrite = mandaDao.getToWriteMandas(userDataSource.mandaUpdateTime.first())
            val originIdList = mandaDataSource.insertManda(toWrite.map { it.asModel() })
            val mandasWithOriginId = toWrite.mapIndexed { index, mandaEntity ->
                mandaEntity.copy(originId = originIdList[index], isSync = true)
            }
            mandaDao.syncInsertManda(mandasWithOriginId)
            userDataSource.setMandaUpdateTime(newUpdateTime(toWrite.map { it.updateTime }))
            true
        } catch (e: Exception) {
            Log.e("TAG", "sync: ${e.message}")
            false
        }
    }

    override suspend fun sync(): Boolean {
        try {
            val toSaveData =
                mandaDataSource.getManda(userDataSource.mandaUpdateTime.first())
                    .map { it.asEntity() }
            val originIdList = toSaveData.map { it.originId }

            val todoIdList = mandaDao.getMandaIdByOriginIds(originIdList)
            val saveData = todoIdList.mapIndexed { index, id ->
                toSaveData[index].copy(
                    id = id ?: 0,
                    isSync = true
                )
            }

            mandaDao.syncInsertManda(saveData).apply {
                val newUpdateTime = toSaveData.maxOfOrNull { it.updateTime }
                if (!newUpdateTime.isNullOrEmpty()) {
                    userDataSource.setMandaUpdateTime(newUpdateTime)
                }
            }
            return true
        } catch (e: Exception) {
            Log.e("TAG", "sync: ${e.message}")
            return false
        }
    }
}
