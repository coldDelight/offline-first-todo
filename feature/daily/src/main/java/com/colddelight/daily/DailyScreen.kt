package com.colddelight.daily

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
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
                    dailyViewModel.changeShowBottomSheet(true, TodoUiState.Default())
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
                bottomSheetUiSate = showBottomSheet,
                onToggleTodo = { id, isDone -> dailyViewModel.toggleTodo(id, isDone) },
                onToggleSheet = { isShow, todo ->
                    dailyViewModel.changeShowBottomSheet(
                        isShow,
                        if (todo == null) TodoUiState.Default() else TodoUiState.Exist(todo)
                    )
                },
                insertTodo = { todo -> dailyViewModel.insertTodo(todo) }
            )
        }
    }
}

@Composable
private fun DailyContentWithState(
    uiState: DailyUiState,
    bottomSheetUiSate: BottomSheetUiState,
    onToggleSheet: (Boolean, Todo?) -> Unit,
    onToggleTodo: (Int, Boolean) -> Unit,
    insertTodo: (Todo) -> Unit
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
                onToggleSheet,
                onToggleTodo,
                bottomSheetUiSate,
                insertTodo
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
    onToggleSheet: (Boolean, Todo?) -> Unit,
    onToggleTodo: (Int, Boolean) -> Unit,
    bottomSheetUiSate: BottomSheetUiState,
    insertTodo: (Todo) -> Unit

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
            if (bottomSheetUiSate is BottomSheetUiState.Up) {
                BottomSheet(
                    onDismissSheet = onToggleSheet,
                    sheetState = sheetState,
                    bottomSheetUiSate.todoUiState.todo,
                    insertTodo
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissSheet: (Boolean, Todo?) -> Unit,
    sheetState: SheetState,
    todo: Todo,
    onInsert: (Todo) -> Unit,
) {
    var todoName by remember { mutableStateOf(todo.name) }
    var todoContent by remember { mutableStateOf(todo.content) }
    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    ModalBottomSheet(
        onDismissRequest = { onDismissSheet(false, null) },
        sheetState = sheetState,
        containerColor = Color.Gray,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                TextField(
                    value = todoName,
                    onValueChange = { todoName = it },
                    label = { Text("제목") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
            item {
                TextField(
                    value = todoContent,
                    onValueChange = { todoContent = it },
                    label = { Text("내용") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            item {
                Button(
                    onClick = {
//                        Log.e("TAG", "BottomSheet: ${todo.id}", )
                        onInsert(Todo(todoName, todoContent, todo.isDone, todo.date, todo.id))
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onDismissSheet(false, null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                ) {
                    Text("완료")
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggleTodo: (Int, Boolean) -> Unit,
    onToggleSheet: (Boolean, Todo) -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleSheet(true, todo) }
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
        { _, _ -> }, { _, _ -> }, BottomSheetUiState.Down, {}
    )

}