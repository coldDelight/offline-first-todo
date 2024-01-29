package com.colddelight.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.colddelight.data.repository.TodoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val todoRepository: TodoRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.e("TAG", "doWork: 새로운 Sync작업의 시작이다")
        try {
            val type = inputData.getInt("type", 1)
            val syncedSuccessfully = when (type) {
                Sync.MANDA -> {
                    awaitAll(
                        async { todoRepository.sync() }
                    ).all { it }
                }

                Sync.TODO -> {
                    awaitAll(
                        async { todoRepository.sync() }
                    ).all { it }
                }

                else -> {
                    awaitAll(
                        async { todoRepository.sync() }
                    ).all { it }
                }
            }
            if (syncedSuccessfully) {
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
        private val syncType = Data.Builder()
            .putInt("type", Sync.ALL)

        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncWorker>().addTag("SyncWork")
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(WorkConstraints)
            .setInputData(syncType.build())
            .build()
    }
}

