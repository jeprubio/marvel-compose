package com.rumosoft.feature_characters.data.network

import com.rumosoft.feature_characters.domain.model.Resource

interface MarvelNetwork {
    suspend fun searchHeroes(offset: Int, limit: Int, nameStartsWith: String): Resource<HeroesResult>

    suspend fun getComicThumbnail(comicId: Int): Resource<String>
}
