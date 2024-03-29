package com.colddelight.data.repository

import com.colddelight.model.Manda
import kotlinx.coroutines.flow.Flow

interface MandaRepository {

    suspend fun initManda()
    fun getAllManda(): Flow<List<Manda>>
    suspend fun updateManda(manda: Manda)
    suspend fun deleteAllManda()

    val isNewUser: Flow<Boolean>
    suspend fun write(): Boolean

    suspend fun sync(): Boolean

    suspend fun reset()

}