package com.colddelight.data

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.colddelight.data.model.WriteType
import com.colddelight.data.model.writeTypeToSync

val WorkConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

fun syncWorkReq(type: WriteType): OneTimeWorkRequest {
    val syncType = writeTypeToSync(type)
    return OneTimeWorkRequestBuilder<SyncWorker>().addTag(SYNC_WORK)
        .setConstraints(WorkConstraints)
        .setInputData(Data.Builder().putInt("type", syncType).build())
        .build()
}

fun writeWorkReq(type: WriteType): OneTimeWorkRequest {
    val syncType = writeTypeToSync(type)
    return OneTimeWorkRequestBuilder<WriteWorker>().addTag(WRITE_WORK)
        .setConstraints(WorkConstraints)
        .setInputData(Data.Builder().putInt("type", syncType).build())
        .build()
}

