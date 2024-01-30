package com.colddelight.network.model

import com.colddelight.model.Todo
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTodo(
    val id: Int = 0,
    val name: String,
    val content: String,
    val is_done: Boolean,
    val date: String,
    val user_id: String = "",
    val update_time: String,
    val is_del: Boolean,
)

fun Todo.asNetWork() = NetworkTodo(
    name = name,
    content = content,
    is_done = isDone,
    date = date.toString(),
    update_time = updateTime,
    is_del = isDel,
    id = originId
)



