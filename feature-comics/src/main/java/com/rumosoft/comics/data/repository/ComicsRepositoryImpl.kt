package com.rumosoft.comics.data.repository

import com.rumosoft.comics.data.network.ComicsNetwork
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.CallInProgressException
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.domain.model.NetworkErrorException
import com.rumosoft.commons.domain.model.NoMoreResultsException
import com.rumosoft.commons.infrastructure.Resource
import timber.log.Timber
import javax.inject.Inject

private const val LIMIT = 20

class ComicsRepositoryImpl @Inject constructor(
    private val network: ComicsNetwork
) : ComicsRepository {
    private var maxPages = Long.MAX_VALUE
    private var currentPage = 1
    private var isRequestInProgress = false

    private var currentHeroes: List<Comic> = emptyList()

    override suspend fun performSearch(
        nameStartsWith: String,
        fromStart: Boolean
    ): Resource<List<Comic>?> {
        if (fromStart) currentPage = 1
        if (currentPage > maxPages) return Resource.Error(NoMoreResultsException("No more data"))
        if (isRequestInProgress) {
            Timber.d("Request is in progress current page: $currentPage")
            return Resource.Error(CallInProgressException("Request is in progress"))
        }
        Timber.d("Performing Network Search")
        val networkResult = performNetworkSearch(nameStartsWith, currentPage)
        if (networkResult is Resource.Success) {
            currentPage++
            Timber.d("Returned results ${networkResult.data?.size}")
        }
        Timber.d("Returned page ${currentPage - 1}")
        return networkResult
    }

    override suspend fun getDetails(comicId: Int): Resource<Comic> {
        return network.fetchComic(comicId)
    }

    private suspend fun performNetworkSearch(
        nameStartsWith: String,
        page: Int
    ): Resource<List<Comic>?> {
        isRequestInProgress = true
        val offset = (page - 1) * LIMIT
        val networkResult = network.searchComics(offset, LIMIT, nameStartsWith)
        isRequestInProgress = false
        return if (currentPage <= maxPages && networkResult is Resource.Success) {
            Resource.Success(parseNetworkResponse(networkResult.data.comics))
        } else
            Resource.Error(NetworkErrorException("Network Error"))
    }

    private fun parseNetworkResponse(comics: List<Comic>?): List<Comic> {
        currentHeroes = if (currentPage > 1) {
            val newList = currentHeroes.toMutableList()
            comics?.let { newList.addAll(it) }
            newList.toList()
        } else {
            comics?.toList() ?: emptyList()
        }
        return currentHeroes
    }
}
