package com.colddelight.network.di

import com.colddelight.network.datasource.MandaDataSource
import com.colddelight.network.datasource.TodoDataSource
import com.colddelight.network.datasourceImpl.MandaDataSourceImpl
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
    fun provideTodoDataSource(
    ): TodoDataSource {
        return TodoDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideMandaDataSource(
    ): MandaDataSource {
        return MandaDataSourceImpl()
    }


}