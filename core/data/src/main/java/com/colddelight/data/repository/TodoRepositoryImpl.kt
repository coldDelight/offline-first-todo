package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.SyncTask
import com.colddelight.data.model.SetAction
import com.colddelight.database.dao.TodoDao
import com.colddelight.database.model.asEntity
import com.colddelight.database.model.asModel
import com.colddelight.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val syncTask: SyncTask,
) : TodoRepository {
    override fun getTodo(dateFlow: Flow<LocalDate>): Flow<List<Todo>> {
        return dateFlow.flatMapLatest { date ->
            todoDao.getTodos(date).map { it.map { todoEntity -> todoEntity.asModel() } }
        }
    }

    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo.asEntity())
        syncTask.syncReq(SetAction.InsertTodo(todo))
    }

    override suspend fun toggleTodo(id: Int, isDone: Boolean) {
        todoDao.toggleTodo(id, isDone)
    }

    override suspend fun delTodo(id: Int) {
        todoDao.deleteTodo(id)
        syncTask.syncReq(SetAction.DelTodo(id))
    }

    override suspend fun sync(action: SetAction) {
        when (action) {
            is SetAction.DelTodo -> {

            }

            is SetAction.InsertTodo -> {

            }

            else -> {

            }
        }
    }

}