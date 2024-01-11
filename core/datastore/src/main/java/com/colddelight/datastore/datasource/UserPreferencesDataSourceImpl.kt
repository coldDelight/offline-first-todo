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
    }

    override val token = dataStore.data.map { preferences ->
        preferences[PreferencesKey.SUPABASE_TOKEN] ?: ""
    }

    override val isNewUser: Flow<Boolean> = dataStore.data.map { preferences ->
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

}