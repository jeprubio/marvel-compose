package com.rumosoft.comics.data.repository

import com.rumosoft.comics.data.network.ComicsNetwork
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.CallInProgressException
import com.rumosoft.commons.domain.model.Comic
import timber.log.Timber
import javax.inject.Inject

private const val LIMIT = 20

class ComicsRepositoryImpl @Inject constructor(
    private val network: ComicsNetwork,
) : ComicsRepository {
    private var isRequestInProgress = false

    override suspend fun performSearch(
        titleStartsWith: String,
        page: Int,
    ): Result<List<Comic>> {
        if (isRequestInProgress) {
            Timber.d("Request is in progress current page: $page")
            return Result.failure(CallInProgressException("Request is in progress"))
        }
        Timber.d("Performing Network Search")
        val networkResult = performNetworkSearch(titleStartsWith, page)
        if (networkResult.isSuccess) {
            Timber.d("Returned result")
        }
        Timber.d("Returned page ${page - 1}")
        return networkResult
    }

    override suspend fun getDetails(comicId: Int): Result<Comic> {
        return network.fetchComic(comicId)
    }

    private suspend fun performNetworkSearch(
        titleStartsWith: String,
        page: Int,
    ): Result<List<Comic>> {
        isRequestInProgress = true
        val offset = (page - 1) * LIMIT
        val networkResult = network.searchComics(offset, LIMIT, titleStartsWith)
        isRequestInProgress = false
        return networkResult.mapCatching { it.comics.orEmpty() }
    }
}
