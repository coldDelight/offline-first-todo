package com.colddelight.network.datasourceImpl

import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasource.TodoDataSource
import com.colddelight.network.model.NetworkId
import com.colddelight.network.model.NetworkTodo
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

    override suspend fun insertTodo(todo: List<NetworkTodo>): List<Int> {
        val result =
            client.postgrest["Todo"].upsert(todo, onConflict = "id") {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }


}