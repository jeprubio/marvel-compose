package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.data.network.apimodels.HeroResults
import retrofit2.http.GET

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun searchHeroes(): HeroResults
}
