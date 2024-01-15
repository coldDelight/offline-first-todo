package com.colddelight.daily

import com.colddelight.model.Todo
import java.time.LocalDate

sealed interface DailyUiState {
    data object Loading : DailyUiState

    data class Error(val msg: String) : DailyUiState

    data class Success(
        val today: String,
        val doneTodos: Int,
        val totTodos: Int,
        val todoList: List<Todo>
    ) : DailyUiState

}