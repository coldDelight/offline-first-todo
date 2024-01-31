package com.colddelight.data.worktask

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.colddelight.data.worker.SYNC_WORK
import com.colddelight.data.worker.WRITE_WORK

class WriteTaskImpl(
    private val context: Context
) : WriteTask {
    override fun writeReq() {
        WorkManager.getInstance(context).beginUniqueWork(
            SYNC_WORK + WRITE_WORK,
            ExistingWorkPolicy.KEEP,
            syncWorkReq()
        ).then(
            writeWorkReq()
        ).enqueue()
    }
}