package com.rumosoft.marvelcompose.infrastructure.di

import com.rumosoft.marvelcompose.data.repository.SearchRepositoryImpl
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(): SearchRepository = SearchRepositoryImpl()
}
