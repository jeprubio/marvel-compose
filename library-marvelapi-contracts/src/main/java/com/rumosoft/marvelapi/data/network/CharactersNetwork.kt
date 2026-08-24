package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.HeroDto
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo

interface CharactersNetwork {
    suspend fun getHeroes(offset: Int, limit: Int): Result<HeroesResult>

    suspend fun getHeroDetails(heroId: Long): Result<HeroDto?>

    suspend fun getComicThumbnail(comicId: Int): Result<String>
}

data class HeroesResult(val paginationInfo: PaginationInfo, val characters: List<HeroDto>?)
