package com.colddelight.network.di

import com.colddelight.network.datasource.TodoDataSource
import com.colddelight.network.datasourceImpl.TodoDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideBookDataSource(
    ): TodoDataSource {
        return TodoDataSourceImpl()
    }


}