package com.colddelight.data.repository

import com.colddelight.database.dao.TodoDao
import com.colddelight.database.model.TodoEntity
import com.colddelight.database.model.asEntity
import com.colddelight.database.model.asModel
import com.colddelight.model.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
) : TodoRepository {
    override fun getTodo(dateFlow: Flow<LocalDate>): Flow<List<Todo>> {

        return dateFlow.flatMapLatest {date->
            todoDao.getTodos(date).map { it.map { todoEntity -> todoEntity.asModel() } }
        }
    }


    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo.asEntity())
    }

    override suspend fun toggleTodo(id: Int, isDone: Boolean) {
        todoDao.toggleTodo(id, isDone)
    }

    override suspend fun delTodo(id: Int) {
        todoDao.deleteTodo(id)
    }

}