package com.colddelight.network.datasource

import com.colddelight.model.Todo


interface TodoDataSource {
    suspend fun getTodo()
    suspend fun insertTodo(todo: Todo)
    suspend fun delTodo(id: Int)
}