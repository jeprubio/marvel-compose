package com.rumosoft.characters.data.network

import com.rumosoft.commons.infrastructure.Resource

interface CharactersNetwork {
    suspend fun searchHeroes(offset: Int, limit: Int, nameStartsWith: String): Resource<HeroesResult>

    suspend fun getComicThumbnail(comicId: Int): Resource<String>
}
