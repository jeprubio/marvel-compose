package com.rumosoft.comics.data.network

import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.commons.data.network.apimodels.ErrorParsingException
import com.rumosoft.commons.data.network.apimodels.toComic
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource
import timber.log.Timber
import javax.inject.Inject

class ComicsNetworkImpl @Inject constructor(
    private val marvelService: MarvelService,
) : ComicsNetwork {
    override suspend fun searchComics(
        offset: Int,
        limit: Int,
        titleStartsWith: String
    ): Resource<ComicsResult> {
        return try {
            val result = marvelService.searchComics(
                offset = offset,
                limit = limit,
                titleStartsWith = titleStartsWith.takeIf { it.isNotEmpty() },
            )
            result.data?.let { data ->
                Resource.Success(
                    ComicsResult(
                        paginationInfo = PaginationInfo(
                            current = data.offset?.div(20) ?: 1,
                            total = data.total?.div(20) ?: Int.MAX_VALUE,
                        ),
                        comics = data.results?.map { it.toComic() }.orEmpty()
                    )
                )
            } ?: run {
                Timber.d("Error parsing results")
                Resource.Error(ErrorParsingException("No results"))
            }
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }

    override suspend fun fetchComic(comicId: Int): Resource<Comic> {
        return try {
            val result = marvelService.searchComic(comicId)
            Resource.Success(result.data?.results?.first()!!.toComic())
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }
}

data class PaginationInfo(
    var current: Int,
    var total: Int
)

data class ComicsResult(val paginationInfo: PaginationInfo, val comics: List<Comic>?)
