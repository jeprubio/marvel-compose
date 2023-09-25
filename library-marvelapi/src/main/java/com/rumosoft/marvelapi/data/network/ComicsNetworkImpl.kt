package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import com.rumosoft.marvelapi.data.network.apimodels.ErrorParsingException
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo
import timber.log.Timber
import javax.inject.Inject

class ComicsNetworkImpl @Inject constructor(
    private val marvelService: MarvelService,
) : ComicsNetwork {
    override suspend fun searchComics(
        offset: Int,
        limit: Int,
        titleStartsWith: String,
    ): Result<ComicsResult> {
        return try {
            val result = marvelService.searchComics(
                offset = offset,
                limit = limit,
                titleStartsWith = titleStartsWith.takeIf { it.isNotEmpty() },
            )
            result.data?.let { data ->
                Result.success(
                    ComicsResult(
                        paginationInfo = PaginationInfo(
                            current = data.offset?.div(20) ?: 1,
                            total = data.total?.div(20) ?: Int.MAX_VALUE,
                        ),
                        comics = data.results.orEmpty(),
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

    override suspend fun fetchComic(comicId: Int): Result<ComicDto> {
        return try {
            val result = marvelService.searchComic(comicId)
            Result.success(result.data?.results?.first()!!)
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Result.failure(e)
        }
    }
}

