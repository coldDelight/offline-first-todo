package com.colddelight.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val userDataSource: UserPreferencesDataSource,

    ): ViewModel() {

    fun delToken() {
        viewModelScope.launch {
            userDataSource.delToken()
        }
    }
}