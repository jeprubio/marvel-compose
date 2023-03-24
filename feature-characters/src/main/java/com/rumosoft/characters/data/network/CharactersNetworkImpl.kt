package com.rumosoft.characters.data.network

import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.commons.data.network.apimodels.ErrorParsingException
import com.rumosoft.commons.data.network.apimodels.getThumbnail
import com.rumosoft.commons.data.network.apimodels.toHero
import com.rumosoft.commons.domain.model.Character
import timber.log.Timber
import javax.inject.Inject

class CharactersNetworkImpl @Inject constructor(
    private val marvelService: MarvelService,
) : CharactersNetwork {
    override suspend fun searchHeroes(offset: Int, limit: Int, nameStartsWith: String): Result<HeroesResult> {
        return try {
            val result = marvelService.searchHeroes(
                offset = offset,
                limit = limit,
                nameStartsWith = nameStartsWith.takeIf { it.isNotEmpty() },
            )
            result.data?.let { data ->
                Result.success(
                    HeroesResult(
                        paginationInfo = PaginationInfo(
                            current = data.offset?.div(20) ?: 1,
                            total = data.total?.div(20) ?: Int.MAX_VALUE,
                        ),
                        characters = data.results?.map { it.toHero() }.orEmpty(),
                    ),
                )
            } ?: run {
                Timber.d("Error parsing results")
                Result.failure(ErrorParsingException("No results"))
            }
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Result.failure(e)
        }
    }

    override suspend fun getComicThumbnail(comicId: Int): Result<String> {
        return try {
            val result = marvelService.searchComic(comicId)
            Result.success(
                result.data?.results?.firstOrNull()?.getThumbnail().orEmpty(),
            )
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Result.failure(e)
        }
    }
}

data class PaginationInfo(
    var current: Int,
    var total: Int,
)

data class HeroesResult(val paginationInfo: PaginationInfo, val characters: List<Character>?)
