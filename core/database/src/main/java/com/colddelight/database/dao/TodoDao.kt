package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.colddelight.database.model.TodoEntity
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun syncInsertTodo(todos: List<TodoEntity>): List<Long>


    @Query("UPDATE todo SET is_done = :isDone WHERE id = :id")
    suspend fun toggleTodo(id: Int, isDone: Boolean)

    @Query("SELECT * FROM todo WHERE date = :date")
    fun getTodos(date: LocalDate): Flow<List<TodoEntity>>


    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteTodo(id: Int)


    @Transaction
    fun getTodoIdByOriginIds(originIdList: List<Int>): List<Int?> {
        return originIdList.map { originId ->
            getTodoIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM todo WHERE origin_id = :originId")
    fun getTodoIdByOriginId(originId: Int): Int?


}