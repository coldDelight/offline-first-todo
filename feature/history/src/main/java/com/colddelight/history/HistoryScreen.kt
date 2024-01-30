package com.colddelight.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.daily.DailyContent
import com.colddelight.daily.DailyUiState
import com.colddelight.daily.DailyViewModel
import com.colddelight.model.Todo
import com.colddelight.model.UiState.BottomSheetUiState
import com.colddelight.model.UiState.TodoUiState
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HistoryScreen(
    viewModel: DailyViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val historyUiState by viewModel.dailyUiState.collectAsStateWithLifecycle()
    val showBottomSheet by viewModel.showBottomSheet.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            HistoryContentWithState(
                uiState = historyUiState,
                bottomSheetUiSate = showBottomSheet,
                onToggleTodo = { id, isDone -> viewModel.toggleTodo(id, isDone) },
                onToggleSheet = { isShow, todo ->
                    viewModel.changeShowBottomSheet(
                        isShow,
                        if (todo == null) TodoUiState.Default() else TodoUiState.Exist(todo)
                    )
                },
                insertTodo = { todo -> viewModel.insertTodo(todo) },
                deleteTodo = { id -> viewModel.deleteTodo(id) },
                onDateClick = { newDate -> viewModel.changeDate(newDate) },
                logOut = { historyViewModel.logOut() }
            )
        }
    }
}

@Composable
private fun HistoryContentWithState(
    uiState: DailyUiState,
    bottomSheetUiSate: BottomSheetUiState,
    onToggleSheet: (Boolean, Todo?) -> Unit,
    onToggleTodo: (Int, Boolean) -> Unit,
    insertTodo: (Todo) -> Unit,
    deleteTodo: (Int) -> Unit,
    onDateClick: (LocalDate) -> Unit,
    logOut: () -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    when (uiState) {
        is DailyUiState.Loading -> {}
        is DailyUiState.Error -> Text(text = uiState.msg)
        is DailyUiState.Success ->
            Column {
                HistoryContent(state, onDateClick, logOut)
                DailyContent(
                    uiState.today,
                    uiState.doneTodos,
                    uiState.totTodos,
                    uiState.todoList,
                    onToggleSheet,
                    onToggleTodo,
                    bottomSheetUiSate,
                    insertTodo,
                    deleteTodo,
                )
            }
    }
}

@Composable
fun HistoryContent(
    state: CalendarState, onDateClick: (LocalDate) -> Unit,
    logOut: () -> Unit
) {
    Column {
        HorizontalCalendar(
            modifier = Modifier.padding(32.dp),
            state = state,
            dayContent = {
                Day(it, onDateClick)
            },
        )
        Button(onClick = { logOut() }) {
            Text(text = "로그아웃")

        }
    }

}

@Composable
fun Day(day: CalendarDay, onDateClick: (LocalDate) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onDateClick(day.date) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}