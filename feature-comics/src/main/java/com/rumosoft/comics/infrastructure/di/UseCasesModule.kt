package com.rumosoft.comics.infrastructure.di

import com.rumosoft.comics.domain.usecase.GetComicsUseCase
import com.rumosoft.comics.domain.usecase.GetComicsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {
    @Binds
    abstract fun bindGetComicsUseCase(
        getComicsUseCase: GetComicsUseCaseImpl
    ): GetComicsUseCase
}
