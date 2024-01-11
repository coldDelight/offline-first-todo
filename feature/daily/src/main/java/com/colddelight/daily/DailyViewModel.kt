package com.colddelight.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val userDataSource: UserPreferencesDataSource,
) : ViewModel() {
    fun updateToken(token: String) {
        viewModelScope.launch {
            userDataSource.saveToken(token)
        }
    }

    fun delToken() {
        viewModelScope.launch {
            userDataSource.delToken()
        }
    }
}