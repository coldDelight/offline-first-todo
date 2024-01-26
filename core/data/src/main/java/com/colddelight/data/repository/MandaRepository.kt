package com.colddelight.data.repository

import com.colddelight.data.model.SetAction
import com.colddelight.model.Manda
import com.colddelight.model.Todo
import kotlinx.coroutines.flow.Flow

interface MandaRepository {

    suspend fun initManda()
    fun getAllManda(): Flow<List<Manda>>
    suspend fun updateManda(manda: Manda)
    suspend fun deleteAllManda()
    suspend fun sync(action: SetAction)

    val isNewUser: Flow<Boolean>
}