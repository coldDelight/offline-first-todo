package com.colddelight.network.datasource

import com.colddelight.network.model.NetworkBook

interface BookDataSource {
    suspend fun getBook()
}