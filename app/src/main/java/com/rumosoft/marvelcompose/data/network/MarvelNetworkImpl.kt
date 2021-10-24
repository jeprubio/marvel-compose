package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.data.network.apimodels.ErrorParsingException
import com.rumosoft.marvelcompose.data.network.apimodels.getThumbnail
import com.rumosoft.marvelcompose.data.network.apimodels.toHero
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import timber.log.Timber
import javax.inject.Inject

class MarvelNetworkImpl @Inject constructor(
    private val marvelService: MarvelService,
) : MarvelNetwork {
    override suspend fun searchHeroes(offset: Int, limit: Int): Resource<HeroesResult> {
        return try {
            val result = marvelService.searchHeroes(
                offset = offset,
                limit = limit,
            )
            if (result.data != null) {
                Resource.Success(
                    HeroesResult(
                        paginationInfo = PaginationInfo(
                            current = result.data.offset!! / 20,
                            total = result.data.total!! / 20,
                        ),
                        heroes = result.data.results?.map { it.toHero() }.orEmpty()
                    )
                )
            } else {
                Timber.d("Error parsing results")
                Resource.Error(ErrorParsingException("No results"))
            }
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }

    override suspend fun getComicThumbnail(comicId: Int): Resource<String> {
        return try {
            val result = marvelService.searchComic(comicId)
            Resource.Success(
                result.data?.results?.firstOrNull()?.getThumbnail().orEmpty()
            )
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

data class HeroesResult(val paginationInfo: PaginationInfo, val heroes: List<Hero>?)
