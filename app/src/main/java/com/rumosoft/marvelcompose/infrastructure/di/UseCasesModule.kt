package com.rumosoft.marvelcompose.infrastructure.di

import com.rumosoft.marvelcompose.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.marvelcompose.domain.usecase.GetComicThumbnailUseCaseImpl
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCase
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {
    @Binds
    abstract fun bindSearchUseCase(
        searchUseCase: SearchUseCaseImpl
    ): SearchUseCase

    @Binds
    abstract fun bindGetComicThumbnailUseCase(
        getComicThumbnailUseCase: GetComicThumbnailUseCaseImpl
    ): GetComicThumbnailUseCase
}
