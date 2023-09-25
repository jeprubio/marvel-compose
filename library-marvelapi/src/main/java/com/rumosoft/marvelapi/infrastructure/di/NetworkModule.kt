package com.rumosoft.marvelapi.infrastructure.di

import com.rumosoft.marvelapi.data.network.CharactersNetwork
import com.rumosoft.marvelapi.data.network.CharactersNetworkImpl
import com.rumosoft.marvelapi.data.network.ComicsNetwork
import com.rumosoft.marvelapi.data.network.ComicsNetworkImpl
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
    abstract fun bindCharactersNetwork(
        marvelNetwork: CharactersNetworkImpl,
    ): CharactersNetwork

    @Binds
    @Singleton
    abstract fun bindComicsNetwork(
        marvelNetwork: ComicsNetworkImpl,
    ): ComicsNetwork
}
