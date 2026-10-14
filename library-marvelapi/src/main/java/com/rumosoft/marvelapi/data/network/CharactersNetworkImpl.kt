package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.ErrorParsingException
import com.rumosoft.marvelapi.data.network.apimodels.HeroDto
import com.rumosoft.marvelapi.data.network.apimodels.getThumbnail
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

class CharactersNetworkImpl @Inject constructor(
    private val marvelService: MarvelService,
) : CharactersNetwork {
    override suspend fun getHeroes(
        offset: Int,
        limit: Int,
    ): Result<HeroesResult> {
        return try {
            val result = marvelService.getHeroes(
                offset = offset,
                limit = limit,
            )
            result.data?.let { data ->
                val results = data.results.orEmpty()
                Result.success(
                    HeroesResult(
                        paginationInfo = buildPaginationInfo(
                            requestOffset = offset,
                            apiOffset = data.offset,
                            apiTotal = data.total,
                            fetchedCount = results.size,
                            limit = limit,
                        ),
                        characters = results,
                    ),
                )
            } ?: run {
                Timber.d("Error parsing results")
                Result.failure(ErrorParsingException("No results"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Result.failure(e)
        }
    }

    override suspend fun getHeroDetails(heroId: Long): Result<HeroDto> {
        return try {
            val result = marvelService.searchHero(heroId)
            result.data?.results?.firstOrNull()?.let {
                Result.success(it)
            } ?: Result.failure(ErrorParsingException("Hero not found"))
        } catch (e: CancellationException) {
            throw e
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
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Result.failure(e)
        }
    }
}
