package com.colddelight.data

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.colddelight.data.model.SetAction

class SyncTaskImpl(
    private val context: Context
) : SyncTask {

    override fun syncReq(action: SetAction) {
        val inputData = when (action) {
            is SetAction.DelManda -> {
                Data.Builder()
                    .putInt("id", action.id).putInt("type", 0)
            }

            is SetAction.DelTodo -> {
                Data.Builder()
                    .putInt("id", action.id).putInt("type", 1)
            }

            is SetAction.InsertManda -> {

                Data.Builder()
                    .putInt("id", action.manda.id).putInt("cnt", action.manda.cnt)
                    .putInt("type", 2)
            }

            is SetAction.InsertTodo -> {
                Data.Builder()
                    .putInt("id", action.todo.id)
                    .putString("name", action.todo.name)
                    .putString("content", action.todo.content)
                    .putString("date", action.todo.date.toString())
                    .putBoolean("isDone", action.todo.isDone)
                    .putInt("type", 3)
            }
        }

        val uploadWorkRequest =
            OneTimeWorkRequestBuilder<CustomWorker>().addTag("SyncWorkManager")
                .setConstraints(syncConstraints).setInputData(inputData.build())
                .build()
        val tmpRequest =
            OneTimeWorkRequestBuilder<TmpWorker>().addTag("임시").setConstraints(syncConstraints)
                .build()

        WorkManager.getInstance(context).beginWith(uploadWorkRequest).then(tmpRequest).enqueue()
    }


}