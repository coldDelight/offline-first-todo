package com.colddelight.network.model


import kotlinx.serialization.Serializable

@Serializable
data class NetworkManda(
    val id: Int = 0,
    val cnt: Int,
    val update_time: String,
    val user_id: String = "",
)




