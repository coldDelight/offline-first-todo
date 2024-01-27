package com.colddelight.network.model

import com.colddelight.model.Todo
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTodo(
    val id: Int = 0,
    val is_done: Boolean,
    val name: String,
    val content: String,
    val date: String,
    val room_id: Int,
//    val user_id: String,
)

fun Todo.asNetWork() = NetworkTodo(
    name = name,
    content = content,
    is_done = isDone,
    date = date.toString(),
    room_id = id
)