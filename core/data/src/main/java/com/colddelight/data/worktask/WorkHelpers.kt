package com.colddelight.data.worktask

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.colddelight.data.worker.SYNC_WORK
import com.colddelight.data.worker.SyncWorker
import com.colddelight.data.worker.WRITE_WORK
import com.colddelight.data.worker.WriteWorker

val WorkConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

fun syncWorkReq(): OneTimeWorkRequest {
    return OneTimeWorkRequestBuilder<SyncWorker>().addTag(SYNC_WORK)
        .setConstraints(WorkConstraints)
        .build()
}

fun writeWorkReq(): OneTimeWorkRequest {
    return OneTimeWorkRequestBuilder<WriteWorker>().addTag(WRITE_WORK)
        .setConstraints(WorkConstraints)
        .build()
}

