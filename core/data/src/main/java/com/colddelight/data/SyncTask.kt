package com.colddelight.data

import androidx.work.Constraints
import androidx.work.NetworkType
import com.colddelight.data.model.SetAction

interface SyncTask {
    fun syncReq(action: SetAction)
}