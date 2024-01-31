package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.worktask.WriteTask
import com.colddelight.data.util.newUpdateTime
import com.colddelight.data.util.updatedTimeStamp
import com.colddelight.database.dao.TodoDao
import com.colddelight.database.model.asEntity
import com.colddelight.database.model.asModel
import com.colddelight.database.model.asNetworkModel
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Todo
import com.colddelight.network.datasource.TodoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val writeTask: WriteTask,
    private val todoDataSource: TodoDataSource,
    private val userDataSource: UserPreferencesDataSource,
) : TodoRepository {
    override fun getTodo(dateFlow: Flow<LocalDate>): Flow<List<Todo>> {

        return dateFlow.flatMapLatest { date ->
            todoDao.getTodos(date).map { it.map { todoEntity -> todoEntity.asModel() } }
        }
    }

    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(
            todo.copy(updateTime = updatedTimeStamp(), isSync = false).asEntity()
        )
        writeTask.writeReq()
    }

    override suspend fun toggleTodo(id: Int, isDone: Boolean) {
        todoDao.toggleTodo(id, isDone, updatedTimeStamp())
        writeTask.writeReq()
    }

    override suspend fun delTodo(id: Int) {
        todoDao.deleteTodo(id, updatedTimeStamp())
        writeTask.writeReq()
    }

    override suspend fun write(): Boolean {
        return try {
            val toWrite = todoDao.getToWriteTodos(userDataSource.todoUpdateTime.first())
            val originIdList = todoDataSource.insertTodo(toWrite.map { it.asNetworkModel() })
            val todosWithOriginId = toWrite.mapIndexed { index, todoEntity ->
                todoEntity.copy(originId = originIdList[index], isSync = true)
            }
            todoDao.syncInsertTodo(todosWithOriginId).run {
                userDataSource.setTodoUpdateTime(newUpdateTime(toWrite.map { it.updateTime }))
            }
            true
        } catch (e: Exception) {
            Log.e("TAG", "sync: ${e.message}")
            false
        }
    }

    override suspend fun sync(): Boolean {
        try {
            val toSync =
                todoDataSource.getTodo(userDataSource.todoUpdateTime.first()).map { it.asEntity() }
            val originIdList = toSync.map { it.originId }
            val todoIdList = todoDao.getTodoIdByOriginIds(originIdList)
            val toSave = todoIdList.mapIndexed { index, id ->
                toSync[index].copy(
                    id = id ?: 0,
                    isSync = true
                )
            }

            todoDao.syncInsertTodo(toSave).run {
                userDataSource.setTodoUpdateTime(newUpdateTime(toSync.map { it.updateTime }))
            }
            return true
        } catch (e: Exception) {
            Log.e("TAG", "sync: ${e.message}")
            return false
        }
    }

    override suspend fun reset() {
        todoDao.deleteAllTodo()
        userDataSource.setTodoUpdateTime("0")
    }
}