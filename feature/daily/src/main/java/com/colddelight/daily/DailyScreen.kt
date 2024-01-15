package com.colddelight.daily

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.model.Todo

@Composable
fun DailyScreen(
    dailyViewModel: DailyViewModel = hiltViewModel(),
) {

    val dailyUiState by dailyViewModel.dailyUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DailyContentWithState(
                uiState = dailyUiState
            )
        }
    }
}

@Composable
private fun DailyContentWithState(
    uiState: DailyUiState,
) {
    when (uiState) {
        is DailyUiState.Loading -> {}
        is DailyUiState.Error -> Text(text = uiState.msg)
        is DailyUiState.Success ->
            DailyContent(
                uiState.today,
                uiState.doneTodos,
                uiState.totTodos,
                uiState.todoList,
            )
    }
}

@Composable
fun DailyContent(
    today: String,
    doneTodos: Int,
    totTodos: Int,
    todoList: List<Todo>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(text = today)
        }
        item {
            Text(text = "$doneTodos / $totTodos")
        }
        items(todoList) {
            TodoItem(it)
        }

    }

}

@Composable
fun TodoItem(todo: Todo) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = todo.name)
        Text(text = todo.content)

    }

}