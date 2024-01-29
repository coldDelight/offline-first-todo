package com.colddelight.network.datasourceImpl

import android.util.Log
import com.colddelight.model.Todo
import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasource.TodoDataSource
import com.colddelight.network.model.NetworkTodo
import com.colddelight.network.model.asNetWork
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest

class TodoDataSourceImpl() : TodoDataSource {
    override suspend fun getTodo(update: String): List<NetworkTodo> {
        return client.postgrest["Todo"].select {
            filter {
                NetworkTodo::update_time gt update
            }
        }.decodeList()
    }

    override suspend fun insertTodo(todo: Todo) {
        val d = client.postgrest["Todo"].upsert(todo.asNetWork(), onConflict = "id")

//        Log.e("TAG", "insertTodo:${d.decodeSingle<NetworkTodo>().name} 업로드 ")
//        client.from("Todo").upsert()
//        val d = client.postgrest["Todo"].insert(todo.asNetWork())
//        d.decodeSingle<NetworkTodo>().id
    }

    override suspend fun delTodo(id: Int) {
        client.postgrest["Todo"].delete {
            filter {
                NetworkTodo::id eq id
            }
        }
    }
}