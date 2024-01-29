package com.colddelight.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    val token: Flow<String>
    val updateTime: Flow<String>
    val isNewUser: Flow<Boolean>

    suspend fun saveToken(token: String)
    suspend fun delToken()

    suspend fun saveIsNewUser()
    suspend fun delIsNewUser()

    suspend fun setUpdateTime(newUpdateTime: String)


}