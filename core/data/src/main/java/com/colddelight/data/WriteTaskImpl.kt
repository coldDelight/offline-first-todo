package com.colddelight.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.colddelight.data.model.WriteType

class WriteTaskImpl(
    private val context: Context
) : WriteTask {
    override fun writeReq(type: WriteType) {
        writeWorkReq(type)
        WorkManager.getInstance(context).beginUniqueWork(
            WRITE_WORK,
            ExistingWorkPolicy.KEEP,
            syncWorkReq(type)
        ).then(
            writeWorkReq(type)
        ).enqueue()
    }
}