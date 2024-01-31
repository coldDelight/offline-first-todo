package com.colddelight.network.datasourceImpl

import com.colddelight.network.SupabaseClient
import com.colddelight.network.datasource.MandaDataSource
import com.colddelight.network.model.NetworkId
import com.colddelight.network.model.NetworkManda
import com.colddelight.network.model.NetworkTodo
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class MandaDataSourceImpl() : MandaDataSource {
    override suspend fun getManda(update: String): List<NetworkManda> {
        return SupabaseClient.client.postgrest["Manda"].select {
            filter {
                NetworkTodo::update_time gt update
            }
        }.decodeList()
    }

    override suspend fun insertManda(manda: List<NetworkManda>): List<Int> {
        val result =
            SupabaseClient.client.postgrest["Manda"].upsert(
                manda, onConflict = "id"
            ) {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }

}