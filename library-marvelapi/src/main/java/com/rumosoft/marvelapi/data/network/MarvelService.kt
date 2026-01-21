package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.ComicResults
import com.rumosoft.marvelapi.data.network.apimodels.HeroResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun getHeroes(
        @Query(OFFSET) offset: Int = 0,
        @Query(LIMIT) limit: Int = 20,
    ): HeroResults

    @GET("/v1/public/characters/{$CHARACTER_ID}")
    suspend fun searchHero(
        @Path(CHARACTER_ID) characterId: Long,
    ): HeroResults

    @GET("/v1/public/comics")
    suspend fun getComics(
        @Query(OFFSET) offset: Int = 0,
        @Query(LIMIT) limit: Int = 20,
    ): ComicResults

    @GET("/v1/public/comics/{$COMIC_ID}")
    suspend fun searchComic(
        @Path(COMIC_ID) comicId: Int,
    ): ComicResults

    companion object {
        const val CHARACTER_ID = "characterId"
        const val COMIC_ID = "comicId"
        const val OFFSET = "offset"
        const val LIMIT = "limit"
    }
}
