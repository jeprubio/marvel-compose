package com.rumosoft.feature_characters.infrastructure.di

import com.rumosoft.feature_characters.data.network.MarvelNetwork
import com.rumosoft.feature_characters.data.network.MarvelNetworkImpl
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
        marvelNetwork: MarvelNetworkImpl,
    ): MarvelNetwork
}
