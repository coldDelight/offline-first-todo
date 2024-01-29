package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.SyncTask
import com.colddelight.data.model.SetAction
import com.colddelight.database.dao.TodoDao
import com.colddelight.database.model.asEntity
import com.colddelight.database.model.asModel
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Todo
import com.colddelight.network.datasource.TodoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val syncTask: SyncTask,
    private val todoDataSource: TodoDataSource,
    private val userDataSource: UserPreferencesDataSource,
) : TodoRepository {
    override fun getTodo(dateFlow: Flow<LocalDate>): Flow<List<Todo>> {

        return dateFlow.flatMapLatest { date ->
            todoDao.getTodos(date).map { it.map { todoEntity -> todoEntity.asModel() } }
        }
    }

    override suspend fun insertTodo(todo: Todo) {
        val id = todoDao.insertTodo(todo.asEntity())
        syncTask.syncReq(SetAction.InsertTodo(todo.copy(id = id.toInt())))
    }

    override suspend fun toggleTodo(id: Int, isDone: Boolean) {
        todoDao.toggleTodo(id, isDone)
    }

    override suspend fun delTodo(id: Int) {
        todoDao.deleteTodo(id)
        syncTask.syncReq(SetAction.DelTodo(id))
    }

    override suspend fun write(action: SetAction) {
        when (action) {
            is SetAction.DelTodo -> {
                todoDataSource.delTodo(action.id)
            }

            is SetAction.InsertTodo -> {
                todoDataSource.insertTodo(action.todo)
            }

            else -> {

            }
        }
    }

    override suspend fun sync(): Boolean {
        val toSaveData =
            todoDataSource.getTodo(userDataSource.updateTime.first()).map { it.asEntity() }
        val originIdList = toSaveData.map { it.originId }
        val todoIdList = todoDao.getTodoIdByOriginIds(originIdList)

        val saveData = todoIdList.mapIndexed { index, id -> toSaveData[index].copy(id = id ?: 0) }

        todoDao.syncInsertTodo(saveData).apply {
            val newUpdateTime = toSaveData.maxOfOrNull { it.updateTime }
            if (!newUpdateTime.isNullOrEmpty()) {
                userDataSource.setUpdateTime(newUpdateTime)
            }
        }
        return true
    }
}