package com.rumosoft.feature_characters.infrastructure.di

import com.rumosoft.feature_characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.feature_characters.domain.usecase.GetComicThumbnailUseCaseImpl
import com.rumosoft.feature_characters.domain.usecase.SearchUseCase
import com.rumosoft.feature_characters.domain.usecase.SearchUseCaseImpl
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
