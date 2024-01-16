package com.colddelight.daily

import com.colddelight.model.Todo
import java.time.LocalDate

sealed interface BottomSheetUiState {
    data object Down : BottomSheetUiState
    data class Up(val todoUiState: TodoUiState) : BottomSheetUiState
}

sealed interface TodoUiState {
    val todo: Todo

    data class Default(
        override val todo: Todo = Todo("", "", false, LocalDate.now())
    ) : TodoUiState

    data class Exist(override val todo: Todo) : TodoUiState
}