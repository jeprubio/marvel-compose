package com.rumosoft.comics.data.repository

import com.rumosoft.comics.data.network.ComicsNetwork
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.CallInProgressException
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.domain.model.NetworkErrorException
import com.rumosoft.commons.infrastructure.Resource
import timber.log.Timber
import javax.inject.Inject

private const val LIMIT = 20

class ComicsRepositoryImpl @Inject constructor(
    private val network: ComicsNetwork,
) : ComicsRepository {
    private var isRequestInProgress = false

    private var currentHeroes: List<Comic> = emptyList()

    override suspend fun performSearch(
        titleStartsWith: String,
        page: Int,
    ): Resource<List<Comic>?> {
        if (isRequestInProgress) {
            Timber.d("Request is in progress current page: $page")
            return Resource.Error(CallInProgressException("Request is in progress"))
        }
        Timber.d("Performing Network Search")
        val networkResult = performNetworkSearch(titleStartsWith, page)
        if (networkResult is Resource.Success) {
            Timber.d("Returned results ${networkResult.data?.size}")
        }
        Timber.d("Returned page ${page - 1}")
        return networkResult
    }

    override suspend fun getDetails(comicId: Int): Resource<Comic> {
        return network.fetchComic(comicId)
    }

    private suspend fun performNetworkSearch(
        titleStartsWith: String,
        page: Int,
    ): Resource<List<Comic>?> {
        isRequestInProgress = true
        val offset = (page - 1) * LIMIT
        val networkResult = network.searchComics(offset, LIMIT, titleStartsWith)
        isRequestInProgress = false
        return if (networkResult is Resource.Success) {
            Resource.Success(parseNetworkResponse(networkResult.data.comics, page))
        } else {
            Resource.Error(NetworkErrorException("Network Error"))
        }
    }

    private fun parseNetworkResponse(
        comics: List<Comic>?,
        page: Int,
    ): List<Comic> {
        currentHeroes = if (page > 1) {
            val newList = currentHeroes.toMutableList()
            comics?.let { newList.addAll(it) }
            newList.toList()
        } else {
            comics?.toList() ?: emptyList()
        }
        return currentHeroes
    }
}
