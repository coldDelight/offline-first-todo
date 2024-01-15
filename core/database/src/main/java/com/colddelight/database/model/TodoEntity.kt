package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.Todo
import java.time.LocalDate

@Entity(tableName = "todo")
data class TodoEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "date") val date: LocalDate,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun TodoEntity.asModel() = Todo(
    name = name,
    content = content,
    isDone = isDone,
    date = date,
    id = id
)


fun Todo.asEntity() = TodoEntity(
    name = name,
    content = content,
    isDone = isDone,
    date = date
)