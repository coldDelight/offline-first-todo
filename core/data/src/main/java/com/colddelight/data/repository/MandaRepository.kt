package com.colddelight.data.repository

import com.colddelight.model.Manda
import com.colddelight.model.Todo
import kotlinx.coroutines.flow.Flow

interface MandaRepository {

    suspend fun initManda()
    fun getAllManda(): Flow<List<Manda>>
    suspend fun updateManda(id: Int, cnt: Int)
    suspend fun deleteAllManda()
}