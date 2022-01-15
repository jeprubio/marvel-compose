package com.rumosoft.feature_characters.infrastructure.di

import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.commons.infrastructure.interceptors.LoggingInterceptor
import com.rumosoft.commons.infrastructure.interceptors.MarvelInterceptor
import com.rumosoft.components.BuildConfig
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
object ServiceModule {
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
    fun provideMarvelService(
        okHttpClient: OkHttpClient,
        baseUrl: String,
    ): MarvelService =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MarvelService::class.java)
}
