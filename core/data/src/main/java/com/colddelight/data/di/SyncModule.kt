package com.colddelight.data.di

import android.content.Context
import com.colddelight.data.worktask.WriteTask
import com.colddelight.data.worktask.WriteTaskImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncModule {
    @Provides
    @Singleton
    fun provideSyncTask(@ApplicationContext appContext: Context): WriteTask {
        return WriteTaskImpl(appContext)
    }
}
