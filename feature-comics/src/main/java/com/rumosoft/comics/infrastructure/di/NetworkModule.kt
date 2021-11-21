package com.rumosoft.comics.infrastructure.di

import com.rumosoft.comics.data.network.ComicsNetwork
import com.rumosoft.comics.data.network.ComicsNetworkImpl
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
        marvelNetwork: ComicsNetworkImpl,
    ): ComicsNetwork
}
