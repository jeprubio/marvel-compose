package com.rumosoft.marvelcompose.infrastructure.di

import com.rumosoft.marvelcompose.BuildConfig
import com.rumosoft.marvelcompose.data.network.MarvelNetwork
import com.rumosoft.marvelcompose.data.network.MarvelNetworkImpl
import com.rumosoft.marvelcompose.data.network.MarvelService
import com.rumosoft.marvelcompose.infrastructure.interceptors.LoggingInterceptor
import com.rumosoft.marvelcompose.infrastructure.interceptors.MarvelInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient().newBuilder().apply {
        addInterceptor(MarvelInterceptor())
        addInterceptor(LoggingInterceptor())
    }.build()

    @Provides
    @Singleton
    fun provideSwapiService(
        okHttpClient: OkHttpClient,
        baseUrl: String,
    ): MarvelService =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MarvelService::class.java)

    @Provides
    @Singleton
    fun providesNetwork(
        marvelService: MarvelService,
    ): MarvelNetwork = MarvelNetworkImpl(marvelService)
}
