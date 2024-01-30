package com.colddelight.network.datasource

import com.colddelight.model.Manda
import com.colddelight.network.model.NetworkManda

interface MandaDataSource {

    suspend fun getManda(update: String): List<NetworkManda>
    suspend fun insertManda(manda: List<Manda>):List<Int>
    suspend fun delManda(id: Int)
}