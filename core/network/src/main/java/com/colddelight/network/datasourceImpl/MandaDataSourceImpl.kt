package com.colddelight.network.datasourceImpl

import com.colddelight.model.Manda
import com.colddelight.network.SupabaseClient
import com.colddelight.network.datasource.MandaDataSource
import com.colddelight.network.model.NetworkId
import com.colddelight.network.model.NetworkManda
import com.colddelight.network.model.NetworkTodo
import com.colddelight.network.model.asNetWork
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

    override suspend fun insertManda(manda: List<Manda>): List<Int> {
        val result =
            SupabaseClient.client.postgrest["Manda"].upsert(
                manda.map { it.asNetWork() },
                onConflict = "id"
            ) {
                select(Columns.list("id"))
            }.decodeList<NetworkId>()
        return result.map { it.id }
    }

    override suspend fun delManda(id: Int) {
        SupabaseClient.client.postgrest["Manda"].delete {
            filter {
                NetworkTodo::id eq id
            }
        }
    }

}