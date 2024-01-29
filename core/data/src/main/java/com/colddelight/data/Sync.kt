package com.colddelight.data

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager

object Sync {
    fun initialize(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SYNC_WORK,
                ExistingWorkPolicy.KEEP,
                SyncWorker.startUpSyncWork(),
            )
        }
    }

    internal const val ALL = 1
    internal const val TODO = 2
    internal const val MANDA = 3
}

internal const val SYNC_WORK = "SyncWork"

