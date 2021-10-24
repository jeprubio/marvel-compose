package com.rumosoft.marvelcompose.data.repository

import com.rumosoft.marvelcompose.data.network.MarvelNetwork
import com.rumosoft.marvelcompose.domain.model.CallInProgressException
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.NoMoreResultsException
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import timber.log.Timber
import javax.inject.Inject

private const val LIMIT = 20

class SearchRepositoryImpl @Inject constructor(
    private val network: MarvelNetwork,
) : SearchRepository {
    private var maxPages = Long.MAX_VALUE
    private var currentPage = 1
    private var isRequestInProgress = false

    override suspend fun performSearch(): Resource<List<Hero>?> {
        if (currentPage > maxPages) {
            return Resource.Error(NoMoreResultsException("No more data"))
        }
        if (isRequestInProgress) {
            Timber.d("Request is in progress current page: $currentPage")
            return Resource.Error(CallInProgressException("Request is in progress"))
        }
        Timber.d("Performing Network Search")
        val networkResult = performNetworkSearch(currentPage)
        if (networkResult is Resource.Success) {
            currentPage++
            Timber.d("Returned results ${networkResult.data?.size}")
        }
        Timber.d("Returned page ${currentPage - 1}")
        return networkResult
    }

    override suspend fun getThumbnail(comicId: Int): Resource<String> {
        return network.getComicThumbnail(comicId)
    }

    private suspend fun performNetworkSearch(page: Int): Resource<List<Hero>?> {
        isRequestInProgress = true
        val offset = (page - 1) * LIMIT
        val networkResult = network.searchHeroes(offset, LIMIT)
        isRequestInProgress = false
        return if (currentPage <= maxPages && networkResult is Resource.Success)
            Resource.Success(networkResult.data.heroes)
        else
            Resource.Error(NoMoreResultsException("No more data"))
    }
}
