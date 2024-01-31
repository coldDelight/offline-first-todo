package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.Todo
import com.colddelight.model.util.DateUtil.stringToDate
import com.colddelight.network.model.NetworkTodo
import java.time.LocalDate

@Entity(tableName = "todo")
data class TodoEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "update_time") val updateTime: String,
    @ColumnInfo(name = "is_del") val isDel: Boolean,
    @ColumnInfo(name = "is_sync") val isSync: Boolean,
    @ColumnInfo(name = "origin_id") val originId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun TodoEntity.asModel() = Todo(
    name = name,
    content = content,
    isDone = isDone,
    date = date,
    updateTime = updateTime,
    isDel = isDel,
    isSync = isSync,
    originId = originId,
    id = id
)

fun TodoEntity.asNetworkModel() = NetworkTodo(
    name = name,
    content = content,
    is_done = isDone,
    date = date.toString(),
    update_time = updateTime,
    is_del = isDel,
    id = originId
)

fun Todo.asEntity() = TodoEntity(
    name = name,
    content = content,
    isDone = isDone,
    date = date,
    updateTime = updateTime,
    isDel = isDel,
    isSync = isSync,
    originId = originId,
    id = id
)

fun NetworkTodo.asEntity() = TodoEntity(
    name = name,
    content = content,
    isDone = is_done,
    date = stringToDate(date),
    updateTime = update_time,
    isDel = is_del,
    isSync = true,
    originId = id,
)