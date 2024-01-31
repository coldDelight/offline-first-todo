package com.colddelight.network.datasource

import com.colddelight.network.model.NetworkManda

interface MandaDataSource {

    suspend fun getManda(update: String): List<NetworkManda>
    suspend fun insertManda(manda: List<NetworkManda>):List<Int>
}