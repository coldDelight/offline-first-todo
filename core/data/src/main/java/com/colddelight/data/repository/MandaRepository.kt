package com.colddelight.data.repository

import com.colddelight.data.model.WriteType
import com.colddelight.model.Manda
import kotlinx.coroutines.flow.Flow

interface MandaRepository {

    suspend fun initManda()
    fun getAllManda(): Flow<List<Manda>>
    suspend fun updateManda(manda: Manda)
    suspend fun deleteAllManda()
    suspend fun sync(action: WriteType)

    val isNewUser: Flow<Boolean>
}