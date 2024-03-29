package com.colddelight.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.MandaRepository
import com.colddelight.data.repository.TodoRepository
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val userDataSource: UserPreferencesDataSource,
    private val todoRepository: TodoRepository,
    private val mandaRepository: MandaRepository,
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            userDataSource.delIsNewUser()
            userDataSource.delToken()
            todoRepository.reset()
            mandaRepository.reset()
        }
    }
}