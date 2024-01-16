package com.colddelight.data.repository

import com.colddelight.database.dao.TodoDao
import com.colddelight.database.model.TodoEntity
import com.colddelight.database.model.asEntity
import com.colddelight.database.model.asModel
import com.colddelight.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
) : TodoRepository {
    override fun getTodo(date: LocalDate): Flow<List<Todo>> {
        return todoDao.getTodos(date).map { it.map { todoEntity -> todoEntity.asModel() } }
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