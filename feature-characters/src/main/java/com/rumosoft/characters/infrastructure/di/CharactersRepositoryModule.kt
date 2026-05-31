package com.rumosoft.characters.infrastructure.di

import com.rumosoft.characters.data.repository.CharactersRepositoryImpl
import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharactersRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        charactersRepository: CharactersRepositoryImpl,
    ): CharactersRepository
}
