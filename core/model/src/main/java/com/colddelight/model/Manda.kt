package com.colddelight.model

import java.time.LocalDate

data class Manda(
    val cnt: Int,
    val id: Int = 0,
    val updateTime:String,
    val isSync: Boolean = false,
    val originId: Int = 0,
)