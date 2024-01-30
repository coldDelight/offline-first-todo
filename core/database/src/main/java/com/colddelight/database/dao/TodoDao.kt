package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.colddelight.database.model.TodoEntity
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun syncInsertTodo(todos: List<TodoEntity>): List<Long>


    @Query("UPDATE todo SET is_done = :isDone,update_time = :updateTime,is_sync = 0 WHERE id = :id")
    suspend fun toggleTodo(id: Int, isDone: Boolean, updateTime: String)

    @Query("SELECT * FROM todo WHERE date = :date AND is_del=0")
    fun getTodos(date: LocalDate): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteTodos(updateTime: String): List<TodoEntity>

    @Query("UPDATE todo SET is_del = 1,update_time= :updateTime,is_sync = 0 WHERE id = :id")
    suspend fun deleteTodo(id: Int, updateTime: String)


    @Transaction
    fun getTodoIdByOriginIds(originIdList: List<Int>): List<Int?> {
        return originIdList.map { originId ->
            getTodoIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM todo WHERE origin_id = :originId AND is_del=0")
    fun getTodoIdByOriginId(originId: Int): Int?

    @Query("DELETE FROM todo")
    suspend fun deleteAll()


}