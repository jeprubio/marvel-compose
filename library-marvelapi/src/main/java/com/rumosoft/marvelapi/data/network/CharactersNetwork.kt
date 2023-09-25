package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.HeroDto
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo

interface CharactersNetwork {
    suspend fun searchHeroes(offset: Int, limit: Int, nameStartsWith: String): Result<HeroesResult>

    suspend fun getComicThumbnail(comicId: Int): Result<String>
}

data class HeroesResult(val paginationInfo: PaginationInfo, val characters: List<HeroDto>?)
