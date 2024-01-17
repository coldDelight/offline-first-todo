package com.colddelight.data.repository

import com.colddelight.database.dao.MandaDao
import com.colddelight.database.model.MandaEntity
import com.colddelight.database.model.asModel
import com.colddelight.model.Manda
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MandaRepositoryImpl @Inject constructor(
    private val mandaDao: MandaDao,
) : MandaRepository {
    override suspend fun initManda() {
        val defaultMandaList = List(9) { MandaEntity(cnt = 0) }
        mandaDao.initManda(defaultMandaList)
    }

    override fun getAllManda(): Flow<List<Manda>> {
        return mandaDao.getAllManda().map { mandaEntity -> mandaEntity.map { it.asModel() } }
    }

    override suspend fun updateManda(id: Int, cnt: Int) {
        mandaDao.updateManda(MandaEntity(cnt, id))
    }

    override suspend fun deleteAllManda() {
        mandaDao.deleteAllManda()
    }
}
