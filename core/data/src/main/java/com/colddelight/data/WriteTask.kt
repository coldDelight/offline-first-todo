package com.colddelight.data

import com.colddelight.data.model.WriteType

interface WriteTask {
    fun writeReq(type: WriteType)
}