package com.rumosoft.commons.infrastructure.di

import com.rumosoft.commons.BuildConfig
import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.commons.infrastructure.interceptors.MarvelInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient().newBuilder().apply {
        addInterceptor(MarvelInterceptor())
        if (BuildConfig.DEBUG) {
            addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
    }.build()

    @Provides
    @Singleton
    fun provideMarvelService(
        okHttpClient: OkHttpClient,
    ): MarvelService =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MarvelService::class.java)
}
