package com.colddelight.model.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtil {
    private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    fun dateToString(date: LocalDate, format: String = DEFAULT_DATE_FORMAT): String {
        val dateFormatter = DateTimeFormatter.ofPattern(format)
        return date.format(dateFormatter)
    }

    fun stringToDate(dateString: String, format: String = DEFAULT_DATE_FORMAT): LocalDate {
        val dateFormatter = DateTimeFormatter.ofPattern(format)
        return LocalDate.parse(dateString, dateFormatter)
    }
}