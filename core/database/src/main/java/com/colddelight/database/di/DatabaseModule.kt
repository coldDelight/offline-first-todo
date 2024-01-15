package com.colddelight.database.di

import android.content.Context
import androidx.room.Room
import com.colddelight.database.MTodoDatabase
import com.colddelight.database.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesMTodoDatabase(
        @ApplicationContext context: Context,
    ): MTodoDatabase = Room.databaseBuilder(
        context,
        MTodoDatabase::class.java,
        "mtodo-database"
    ).build()


    @Provides
    fun providesTodoDao(
        database: MTodoDatabase,
    ): TodoDao = database.todoDao()

}