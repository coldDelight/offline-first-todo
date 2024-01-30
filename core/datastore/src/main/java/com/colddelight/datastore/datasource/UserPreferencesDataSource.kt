package com.colddelight.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    val token: Flow<String>
    val todoUpdateTime: Flow<String>
    val mandaUpdateTime: Flow<String>
    val isNewUser: Flow<Boolean>

    suspend fun saveToken(token: String)
    suspend fun delToken()

    suspend fun saveIsNewUser()
    suspend fun delIsNewUser()

    suspend fun setTodoUpdateTime(newUpdateTime: String)
    suspend fun setMandaUpdateTime(newUpdateTime: String)


}