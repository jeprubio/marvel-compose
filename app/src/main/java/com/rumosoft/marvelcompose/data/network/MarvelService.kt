package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.data.network.apimodels.ComicResults
import com.rumosoft.marvelcompose.data.network.apimodels.HeroResults
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun searchHeroes(): HeroResults

    @GET("/v1/public/comics/{$COMIC_ID}")
    suspend fun searchComic(
        @Path(COMIC_ID) comicId: Int
    ): ComicResults

    companion object {
        const val COMIC_ID = "comicId"
    }
}
