package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.data.network.apimodels.toHero
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import timber.log.Timber
import javax.inject.Inject

class MarvelNetworkImpl @Inject constructor(
    private val marvelService: MarvelService,
) : MarvelNetwork {
    override suspend fun searchHeroes(): Resource<List<Hero>> {
        return try {
            val result = marvelService.searchHeroes()
            Resource.Success(result.data?.results?.map { it.toHero() }.orEmpty())
        } catch (e: Exception) {
            Timber.d("Something went wrong: $e")
            Resource.Error(e)
        }
    }
}
