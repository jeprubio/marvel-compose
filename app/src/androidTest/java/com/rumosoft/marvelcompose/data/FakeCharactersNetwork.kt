package com.rumosoft.marvelcompose.data

import com.rumosoft.characters.data.repository.CHARACTERS_LIMIT
import com.rumosoft.marvelapi.data.network.CharactersNetwork
import com.rumosoft.marvelapi.data.network.HeroesResult
import com.rumosoft.marvelapi.data.network.apimodels.HeroDto
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo
import javax.inject.Inject

class FakeCharactersNetwork @Inject constructor(): CharactersNetwork {
    override suspend fun getHeroes(
        offset: Int,
        limit: Int,
    ): Result<HeroesResult> {
        val heroesResult = HeroesResult(
            paginationInfo = PaginationInfo(
                current = 0,
                total = 0,
            ),
            characters = (offset .. offset + CHARACTERS_LIMIT).map {
                HeroDto(
                    id = it.toLong(),
                    name = "character $it",
                    description = "description $it",
                    thumbnail = null,
                    urls = emptyList(),
                    comics = null,
                )
            }
        )
        return Result.success(heroesResult)
    }

    override suspend fun getHeroDetails(heroId: Long): Result<HeroDto?> {
        val hero = getHeroes(0, 0).getOrNull()?.characters?.find { it.id == heroId }
        return hero?.let {
            Result.success(it)
        } ?: Result.failure(Exception("Hero not found"))
    }

    override suspend fun getComicThumbnail(comicId: Int): Result<String> =
        Result.success("thumbnail")
}
