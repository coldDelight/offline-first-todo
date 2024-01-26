package com.colddelight.data

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.colddelight.data.model.SetAction
import com.colddelight.data.repository.MandaRepository
import com.colddelight.data.repository.TodoRepository
import com.colddelight.model.Manda
import com.colddelight.model.Todo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@HiltWorker
class CustomWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Assisted private val todoRepository: TodoRepository,
    @Assisted private val mandaRepository: MandaRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val type = inputData.getInt("type", 0)
            when (type) {
                0 -> {
                    mandaRepository.sync(SetAction.DelManda(inputData.getInt("id", 0)))
                }

                1 -> {
                    todoRepository.sync(SetAction.DelTodo(inputData.getInt("id", 0)))
                }

                2 -> {
                    val manda = Manda(
                        id = inputData.getInt("id", 0),
                        cnt = inputData.getInt("cnt", 0)
                    )
                    mandaRepository.sync(SetAction.InsertManda(manda))
                }

                3 -> {
                    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val todo = Todo(
                        id = inputData.getInt("id", 0),
                        name = inputData.getString("name") ?: "",
                        content = inputData.getString("content") ?: "",
                        isDone = inputData.getBoolean("isDone", false),
                        date = LocalDate.parse(inputData.getString("date") ?: "", dateFormat),
                    )
                    todoRepository.sync(SetAction.InsertTodo(todo))
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    }
}
