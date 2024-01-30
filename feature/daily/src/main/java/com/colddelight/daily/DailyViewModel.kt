package com.colddelight.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.TodoRepository
import com.colddelight.model.Todo
import com.colddelight.model.UiState.BottomSheetUiState
import com.colddelight.model.UiState.TodoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _showBottomSheet = MutableStateFlow<BottomSheetUiState>(BottomSheetUiState.Down)
    val showBottomSheet: StateFlow<BottomSheetUiState> = _showBottomSheet

    private val _date = MutableStateFlow<LocalDate>(LocalDate.now())
    private val dateFlow: StateFlow<LocalDate> = _date

    fun changeDate(date: LocalDate) {
        _date.value = date
    }

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodo(
                todo.copy(
                    updateTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toString()
                )
            )
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            repository.delTodo(id)
        }
    }

    fun toggleTodo(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            repository.toggleTodo(id, isDone)
        }
    }

    fun changeShowBottomSheet(isShow: Boolean, todoUiState: TodoUiState) {
        viewModelScope.launch {
            if (isShow) {
                _showBottomSheet.value = BottomSheetUiState.Up(todoUiState)
            } else {
                _showBottomSheet.value = BottomSheetUiState.Down
            }
        }
    }

    val dailyUiState: StateFlow<DailyUiState> =
        repository.getTodo(dateFlow).map {
            val totTodos = it.size
            val doneTodos = it.filter { todo -> todo.isDone }.size
            DailyUiState.Success(today = dateFlow.value.toString(), doneTodos, totTodos, it)
        }.catch {
            DailyUiState.Error(it.message ?: "err")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DailyUiState.Loading
        )


}