package com.rumosoft.marvelcompose.infrastructure.di

import com.rumosoft.marvelcompose.domain.usecase.SearchUseCase
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCaseImpl
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    fun provideSearchUseCase(
        searchRepository: SearchRepository
    ): SearchUseCase = SearchUseCaseImpl(searchRepository)
}
