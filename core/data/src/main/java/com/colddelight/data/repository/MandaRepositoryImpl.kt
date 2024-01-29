package com.colddelight.data.repository

import com.colddelight.data.SyncTask
import com.colddelight.data.model.SetAction
import com.colddelight.database.dao.MandaDao
import com.colddelight.database.model.MandaEntity
import com.colddelight.database.model.asEntity
import com.colddelight.database.model.asModel
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Manda
import com.colddelight.network.datasource.TodoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaRepositoryImpl @Inject constructor(
    private val mandaDao: MandaDao,
    private val userDataSource: UserPreferencesDataSource,
    private val todoDataSource: TodoDataSource,
    private val syncTask: SyncTask,
) : MandaRepository {

    override val isNewUser: Flow<Boolean> = userDataSource.isNewUser
    override suspend fun initManda() {
        val defaultMandaList = List(9) { MandaEntity(cnt = 0) }
        mandaDao.initManda(defaultMandaList)
        userDataSource.saveIsNewUser()
    }

    override fun getAllManda(): Flow<List<Manda>> {
        return mandaDao.getAllManda().map { mandaEntity -> mandaEntity.map { it.asModel() } }
    }

    override suspend fun updateManda(manda: Manda) {
        mandaDao.updateManda(manda.asEntity())

        syncTask.syncReq(SetAction.InsertManda(manda))
    }

    override suspend fun deleteAllManda() {
        userDataSource.delIsNewUser()
        mandaDao.deleteAllManda()

        repeat(9) {
            syncTask.syncReq(SetAction.DelManda(it + 1))
        }
    }

    override suspend fun sync(action: SetAction) {
        when (action) {
            is SetAction.DelManda -> {
            }

            is SetAction.InsertManda -> {
            }

            else -> {

            }
        }
    }
}
