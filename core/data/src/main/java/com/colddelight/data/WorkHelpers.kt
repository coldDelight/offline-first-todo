package com.colddelight.data

import androidx.work.Constraints
import androidx.work.NetworkType

val WorkConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()


