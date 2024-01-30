package com.colddelight.data.model

import com.colddelight.data.Sync

sealed class WriteType {
    data object Todo : WriteType()
    data object Manda : WriteType()
}

fun writeTypeToSync(type: WriteType): Int {
    return when (type) {
        is WriteType.Todo -> Sync.TODO
        is WriteType.Manda -> Sync.MANDA
    }
}





