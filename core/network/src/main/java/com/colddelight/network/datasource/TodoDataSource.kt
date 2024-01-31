package com.colddelight.network.datasource

import com.colddelight.model.Todo
import com.colddelight.network.model.NetworkTodo


interface TodoDataSource {
    suspend fun getTodo(update: String): List<NetworkTodo>
    suspend fun insertTodo(todo: List<NetworkTodo>):List<Int>
}