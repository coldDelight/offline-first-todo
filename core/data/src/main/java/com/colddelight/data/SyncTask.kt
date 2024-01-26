package com.colddelight.data

import androidx.work.Constraints
import androidx.work.NetworkType
import com.colddelight.data.model.SetAction

interface SyncTask {
    val syncConstraints: Constraints
        get() = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    fun syncReq(action: SetAction)
}