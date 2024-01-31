package com.colddelight.data.worktask

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.colddelight.data.worker.SYNC_WORK
import com.colddelight.data.worker.SyncWorker

object SyncTask {
    fun initialize(context: Context) {
        WorkManager.getInstance(context).enqueueUniqueWork(
            SYNC_WORK,
            ExistingWorkPolicy.KEEP,
            SyncWorker.startUpSyncWork(),
        )
    }

}
