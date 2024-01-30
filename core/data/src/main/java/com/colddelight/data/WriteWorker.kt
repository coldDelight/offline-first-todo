package com.colddelight.data

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

@HiltWorker
class WriteWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val todoRepository: TodoRepository,
    @Assisted private val mandaRepository: MandaRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val type = inputData.getInt("type", 0)
            val writeSuccessfully = when (type) {
                Sync.MANDA -> {
                    awaitAll(
                        async { todoRepository.write() }
                    ).all { it }
                }

                Sync.TODO -> {
                    awaitAll(
                        async { todoRepository.write() }
                    ).all { it }
                }

                else -> {
                    false
                }
            }

            if (writeSuccessfully) {
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
