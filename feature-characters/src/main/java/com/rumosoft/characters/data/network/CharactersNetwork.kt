package com.rumosoft.characters.data.network

interface CharactersNetwork {
    suspend fun searchHeroes(offset: Int, limit: Int, nameStartsWith: String): Result<HeroesResult>

    suspend fun getComicThumbnail(comicId: Int): Result<String>
}
