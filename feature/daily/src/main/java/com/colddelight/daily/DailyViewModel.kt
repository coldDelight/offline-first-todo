package com.colddelight.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.TodoRepository
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
//    init {
//        viewModelScope.launch {
//            repository.addTmp()
//
//        }
//    }

    private val _showBottomSheet =
        MutableStateFlow<Boolean>(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet


    fun toggleTodo(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            repository.toggleTodo(id, isDone)
        }
    }

    fun changeShowBottomSheet() {
        viewModelScope.launch {
            _showBottomSheet.value = !_showBottomSheet.value
        }
    }

    val dailyUiState: StateFlow<DailyUiState> =
        repository.getTodo(LocalDate.now()).map {
            val totTodos = it.size
            val doneTodos = it.filter { todo -> todo.isDone }.size
            DailyUiState.Success(today = LocalDate.now().toString(), doneTodos, totTodos, it)
        }.catch {
            DailyUiState.Error(it.message ?: "err")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DailyUiState.Loading
        )

}