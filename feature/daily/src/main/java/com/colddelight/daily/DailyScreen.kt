package com.colddelight.daily

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.model.Todo
import java.time.LocalDate

@Composable
fun DailyScreen(
    dailyViewModel: DailyViewModel = hiltViewModel(),
) {
    val dailyUiState by dailyViewModel.dailyUiState.collectAsStateWithLifecycle()
    val showBottomSheet by dailyViewModel.showBottomSheet.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dailyViewModel.changeShowBottomSheet()
                },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DailyContentWithState(
                uiState = dailyUiState,
                showBottomSheet = showBottomSheet,
                onToggleTodo = { id, isDone -> dailyViewModel.toggleTodo(id, isDone) },
                onToggleSheet = { dailyViewModel.changeShowBottomSheet() }
            )
        }
    }
}

@Composable
private fun DailyContentWithState(
    uiState: DailyUiState,
    showBottomSheet: Boolean,
    onToggleSheet: (Boolean) -> Unit,
    onToggleTodo: (Int, Boolean) -> Unit
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
                showBottomSheet,
                onToggleSheet,
                onToggleTodo
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyContent(
    today: String,
    doneTodos: Int,
    totTodos: Int,
    todoList: List<Todo>,
    showBottomSheet: Boolean,
    onToggleSheet: (Boolean) -> Unit,
    onToggleTodo: (Int, Boolean) -> Unit

) {
    val sheetState = rememberModalBottomSheetState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        item {
            Text(text = today)
        }
        item {
            Text(text = "$doneTodos / $totTodos")
        }
        items(todoList) {
            TodoItem(it, onToggleTodo, onToggleSheet)
        }
        item {
            if (showBottomSheet) {
                InsertBottomSheet(
                    onDismissSheet = onToggleSheet,
                    sheetState = sheetState
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertBottomSheet(
    onDismissSheet: (Boolean) -> Unit,
    sheetState: SheetState,

    ) {
    ModalBottomSheet(
        onDismissRequest = { onDismissSheet(false) },
        sheetState = sheetState,
        containerColor = Color.Gray,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
        ) {
            Text("바텀시트 테스트")
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggleTodo: (Int, Boolean) -> Unit,
    onToggleSheet: (Boolean) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleSheet(true) }
            .padding(16.dp)
            .border(1.dp, Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { onToggleTodo(todo.id, it) }
            )
            Column {
                Text(text = todo.name)
                Text(text = todo.content)
            }
        }
    }
}


@Preview
@Composable
fun DailyPreview() {
    val tmp = listOf<Todo>(
        Todo("제목111", "내용11111", false, LocalDate.now()),
        Todo("22222222", "내용133322222222222", false, LocalDate.now()),
        Todo("제목222222", "내용133333331", false, LocalDate.now()),
    )
    DailyContent(
        today = LocalDate.now().toString(),
        doneTodos = 0,
        totTodos = 3,
        todoList = tmp,
        false, {}, { _, _ -> }
    )

}