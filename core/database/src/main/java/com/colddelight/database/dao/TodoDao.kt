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

    //Local
    @Query("SELECT * FROM todo WHERE date = :date AND is_del=0")
    fun getTodos(date: LocalDate): Flow<List<TodoEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Query("UPDATE todo SET is_done = :isDone,update_time = :updateTime,is_sync = 0 WHERE id = :id")
    suspend fun toggleTodo(id: Int, isDone: Boolean, updateTime: String)

    @Query("UPDATE todo SET is_del = 1,update_time= :updateTime,is_sync = 0 WHERE id = :id")
    suspend fun deleteTodo(id: Int, updateTime: String)
    @Query("DELETE FROM todo")
    suspend fun deleteAllTodo()



    //Network
    @Query("SELECT * FROM todo WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteTodos(updateTime: String): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun syncInsertTodo(todos: List<TodoEntity>)

    @Transaction
    fun getTodoIdByOriginIds(originIdList: List<Int>): List<Int?> {
        return originIdList.map { originId ->
            getTodoIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM todo WHERE origin_id = :originId AND is_del=0")
    fun getTodoIdByOriginId(originId: Int): Int?




}