package com.rumosoft.marvelcompose.data

import com.rumosoft.marvelapi.data.network.CharactersNetwork
import com.rumosoft.marvelapi.data.network.ComicsNetwork
import com.rumosoft.marvelapi.infrastructure.di.NetworkModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
abstract class FakeNetworkModule {

    @Binds
    @Singleton
    abstract fun bindCharactersNetwork(
        fakeCharactersNetwork: FakeCharactersNetwork,
    ): CharactersNetwork

    @Binds
    @Singleton
    abstract fun bindComicsNetwork(
        fakeComicsNetwork: FakeComicsNetwork,
    ): ComicsNetwork
}
