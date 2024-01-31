package com.colddelight.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.colddelight.data.worktask.WorkConstraints
import com.colddelight.data.repository.MandaRepository
import com.colddelight.data.repository.TodoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

internal const val SYNC_WORK = "SyncWork"

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val todoRepository: TodoRepository,
    @Assisted private val mandaRepository: MandaRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val syncedSucceed = awaitAll(
                async { todoRepository.sync() },
                async { mandaRepository.sync() }
            ).all { it }
            if (syncedSucceed) {
                Result.success()
            } else {
                Result.retry()
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncWorker>().addTag(SYNC_WORK)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(WorkConstraints)
            .build()
    }
}

