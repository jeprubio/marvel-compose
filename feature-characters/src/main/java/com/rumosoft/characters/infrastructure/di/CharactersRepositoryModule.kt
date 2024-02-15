package com.rumosoft.characters.infrastructure.di

import com.rumosoft.characters.data.repository.SearchRepositoryImpl
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CharactersRepositoryModule {
    @Binds
    abstract fun bindCharacterRepository(
        searchRepository: SearchRepositoryImpl,
    ): SearchRepository
}
