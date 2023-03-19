package com.rumosoft.comics.infrastructure.di

import com.rumosoft.comics.data.repository.ComicsRepositoryImpl
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
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
        comicsRepository: ComicsRepositoryImpl,
    ): ComicsRepository
}
