package com.rumosoft.feature_characters.data.network

import com.rumosoft.feature_characters.data.network.apimodels.ComicResults
import com.rumosoft.feature_characters.data.network.apimodels.HeroResults
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

    @GET("/v1/public/comics/{$COMIC_ID}")
    suspend fun searchComic(
        @Path(COMIC_ID) comicId: Int,
    ): ComicResults

    companion object {
        const val COMIC_ID = "comicId"
        const val OFFSET = "offset"
        const val LIMIT = "limit"
        const val NAME_STARTS_WITH = "nameStartsWith"
    }
}
