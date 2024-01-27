package com.colddelight.network.datasourceImpl

import com.colddelight.model.Todo
import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasource.TodoDataSource
import com.colddelight.network.model.NetworkTodo
import com.colddelight.network.model.asNetWork
import io.github.jan.supabase.postgrest.postgrest

class TodoDataSourceImpl() : TodoDataSource {
    override suspend fun getTodo() {
        val data = client.postgrest["Todo"].select().decodeList<NetworkTodo>()
    }

    override suspend fun insertTodo(todo: Todo) {
        client.postgrest["Todo"].insert(todo.asNetWork())
    }

    override suspend fun delTodo(id: Int) {
        client.postgrest["Todo"].delete {
            filter {
                Todo::id eq id
            }
        }
    }
}