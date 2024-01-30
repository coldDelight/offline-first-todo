package com.colddelight.network.datasourceImpl

import android.util.Log
import com.colddelight.model.Todo
import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasource.TodoDataSource
import com.colddelight.network.model.NetworkId
import com.colddelight.network.model.NetworkTodo
import com.colddelight.network.model.asNetWork
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class TodoDataSourceImpl() : TodoDataSource {
    override suspend fun getTodo(update: String): List<NetworkTodo> {
        return client.postgrest["Todo"].select {
            filter {
                NetworkTodo::update_time gt update
            }
        }.decodeList()
    }

    override suspend fun insertTodo(todo: List<Todo>): List<Int> {
        val result =
            client.postgrest["Todo"].upsert(todo.map { it.asNetWork() }, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }

    override suspend fun delTodo(id: Int) {
        client.postgrest["Todo"].delete {
            filter {
                NetworkTodo::id eq id
            }
        }
    }
}