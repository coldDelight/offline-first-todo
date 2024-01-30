package com.colddelight.mtodo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.colddelight.data.Sync
import com.colddelight.data.WriteWorker
import com.colddelight.data.SyncWorker
import com.colddelight.data.repository.MandaRepository
import com.colddelight.data.repository.TodoRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MTodoApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory


    override val workManagerConfiguration: Configuration
        get() =
            Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(workerFactory)
                .build()

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(this)
    }
}

class CustomWorkerFactory @Inject constructor(
    private val todoRepository: TodoRepository,
    private val mandaRepository: MandaRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name -> SyncWorker(appContext, workerParameters, todoRepository,mandaRepository)
            WriteWorker::class.java.name -> WriteWorker(
                appContext,
                workerParameters,
                todoRepository,
                mandaRepository
            )

            else -> null
        }
    }
}
