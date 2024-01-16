package com.colddelight.model

import java.time.LocalDate

data class Todo(
    val name: String,
    val content: String,
    val isDone: Boolean,
    val date: LocalDate,
    val id: Int = 0
)