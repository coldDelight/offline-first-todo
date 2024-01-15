package com.colddelight.data.di

import com.colddelight.data.repository.TodoRepository
import com.colddelight.data.repository.TodoRepositoryImpl
import com.colddelight.data.util.LoginHelper
import com.colddelight.data.util.LoginHelperImpl
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.datastore.datasource.UserPreferencesDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    abstract fun bindLocalDataSource(
        dataSource: UserPreferencesDataSourceImpl,
    ): UserPreferencesDataSource

    @Binds
    fun bindsLoginHelper(
        loginHelper: LoginHelperImpl
    ): LoginHelper

    @Binds
    fun bindsTodoRepository(
        todoRepository: TodoRepositoryImpl
    ): TodoRepository
}
