package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.ComicResults
import com.rumosoft.marvelapi.data.network.apimodels.HeroResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun searchHeroes(
        @Query(OFFSET) offset: Int = 0,
        @Query(LIMIT) limit: Int = 20,
        @Query(NAME_STARTS_WITH) nameStartsWith: String? = null,
    ): HeroResults

    @GET("/v1/public/characters/{$CHARACTER_ID}")
    suspend fun searchHero(
        @Path(CHARACTER_ID) characterId: Long,
    ): HeroResults

    @GET("/v1/public/comics")
    suspend fun searchComics(
        @Query(OFFSET) offset: Int = 0,
        @Query(LIMIT) limit: Int = 20,
        @Query(TITLE_STARTS_WITH) titleStartsWith: String? = null,
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
        const val NAME_STARTS_WITH = "nameStartsWith"
        const val TITLE_STARTS_WITH = "titleStartsWith"
    }
}
