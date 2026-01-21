package com.rumosoft.characters.infrastructure.di

import com.rumosoft.characters.data.repository.CharactersRepositoryImpl
import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CharactersRepositoryModule {
    @Binds
    abstract fun bindCharacterRepository(
        charactersRepository: CharactersRepositoryImpl,
    ): CharactersRepository
}
