package com.colddelight.data.util

import java.time.LocalDateTime
import java.time.ZoneId

fun updatedTimeStamp(): String {
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
//    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toString()
    //2024-01-29T22:00:35.101+09:00[Asia/Seoul]
}

fun newUpdateTime(list: List<String>): String {
    return list.max()
}

