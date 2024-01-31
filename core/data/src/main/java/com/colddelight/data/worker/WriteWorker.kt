package com.colddelight.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.colddelight.data.repository.MandaRepository
import com.colddelight.data.repository.TodoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

internal const val WRITE_WORK = "WriteWork"

@HiltWorker
class WriteWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val todoRepository: TodoRepository,
    @Assisted private val mandaRepository: MandaRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val writeSucceed = awaitAll(
                async { todoRepository.write() },
                async { mandaRepository.write() }
            ).all { it }
            if (writeSucceed) {
                Result.success()
            } else {
                Result.retry()
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}

