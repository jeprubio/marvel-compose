package com.rumosoft.characters.infrastructure.di

import com.rumosoft.characters.data.network.CharactersNetwork
import com.rumosoft.characters.data.network.CharactersNetworkImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetwork(
        marvelNetwork: CharactersNetworkImpl,
    ): CharactersNetwork
}
