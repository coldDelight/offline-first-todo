package com.colddelight.network.datasourceImpl

import android.util.Log
import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasource.BookDataSource
import com.colddelight.network.model.NetworkBook
import io.github.jan.supabase.postgrest.postgrest

class BookDataSourceImpl() : BookDataSource {
    override suspend fun getBook() {
        val data = client.postgrest["Book"].select().decodeList<NetworkBook>()
        Log.e("TAG", "getBook: $data")
    }
}