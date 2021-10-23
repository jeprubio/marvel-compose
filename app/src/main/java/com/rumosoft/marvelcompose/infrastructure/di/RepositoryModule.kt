package com.rumosoft.marvelcompose.infrastructure.di

import com.rumosoft.marvelcompose.data.repository.SearchRepositoryImpl
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository
}
