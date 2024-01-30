package com.colddelight.network.model

import com.colddelight.model.Manda
import com.colddelight.model.Todo
import kotlinx.serialization.Serializable

@Serializable
data class NetworkManda(
    val id: Int = 0,
    val cnt: Int,
    val update_time: String,
    val user_id: String = "",
)

fun Manda.asNetWork() = NetworkManda(
    cnt = cnt,
    update_time = updateTime,
    id = originId
)



