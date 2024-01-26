package com.colddelight.data.repository

import com.colddelight.data.model.SetAction
import com.colddelight.model.Todo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TodoRepository {
    fun getTodo(date: Flow<LocalDate>): Flow<List<Todo>>

    suspend fun insertTodo(todo: Todo)
    suspend fun toggleTodo(id:Int,isDone:Boolean)
    suspend fun delTodo(id: Int)


    suspend fun sync(action: SetAction)
}