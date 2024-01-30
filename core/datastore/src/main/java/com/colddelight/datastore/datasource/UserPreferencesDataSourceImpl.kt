package com.colddelight.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class UserPreferencesDataSourceImpl @Inject constructor(
    @Named("user") private val dataStore: DataStore<Preferences>,
) : UserPreferencesDataSource {

    object PreferencesKey {
        val SUPABASE_TOKEN = stringPreferencesKey("SUPABASE_TOKEN")
        val IS_NEWUSER = booleanPreferencesKey("IS_NEWUSER")
        val TODO_UPDATE = stringPreferencesKey("TODO_UPDATE")
        val MANDA_UPDATE = stringPreferencesKey("MANDA_UPDATE")
    }

    override val token = dataStore.data.map { preferences ->
        preferences[PreferencesKey.SUPABASE_TOKEN] ?: ""
    }

    override val todoUpdateTime = dataStore.data.map { preferences ->
        preferences[PreferencesKey.TODO_UPDATE] ?: "0"
    }
    override val mandaUpdateTime = dataStore.data.map { preferences ->
        preferences[PreferencesKey.MANDA_UPDATE] ?: "0"
    }

    override val isNewUser = dataStore.data.map { preferences ->
        preferences[PreferencesKey.IS_NEWUSER] ?: true
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SUPABASE_TOKEN] = token
        }
    }

    override suspend fun delToken() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SUPABASE_TOKEN] = ""
        }
    }

    override suspend fun saveIsNewUser() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.IS_NEWUSER] = false
        }
    }

    override suspend fun delIsNewUser() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.IS_NEWUSER] = true
        }
    }

    override suspend fun setTodoUpdateTime(newUpdateTime: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.TODO_UPDATE] = newUpdateTime
        }
    }

    override suspend fun setMandaUpdateTime(newUpdateTime: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.MANDA_UPDATE] = newUpdateTime
        }
    }

}