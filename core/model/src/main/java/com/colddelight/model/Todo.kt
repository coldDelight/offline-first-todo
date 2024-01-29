package com.colddelight.model

import java.time.LocalDate

data class Todo(
    val name: String,
    val content: String,
    val isDone: Boolean,
    val date: LocalDate,
    val updateTime:String,
    val isDel: Boolean = false,
    val isSync: Boolean = false,
    val originId: Int = 0,
    val id: Int = 0
)
