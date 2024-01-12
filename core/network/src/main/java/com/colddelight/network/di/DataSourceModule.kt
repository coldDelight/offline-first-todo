package com.colddelight.network.di

import com.colddelight.network.datasource.BookDataSource
import com.colddelight.network.datasourceImpl.BookDataSourceImpl
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
    ): BookDataSource {
        return BookDataSourceImpl()
    }


}