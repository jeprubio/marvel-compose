package com.rumosoft.comics.infrastructure.di

import com.rumosoft.comics.data.repository.ComicsRepositoryImpl
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(
        comicsRepository: ComicsRepositoryImpl,
    ): ComicsRepository
}
