package com.colddelight.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkBook(
    val id: Int = 0,
    val name: String,
)