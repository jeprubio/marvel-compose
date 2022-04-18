package com.rumosoft.characters.data.repository

import com.rumosoft.characters.data.network.CharactersNetwork
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.commons.domain.model.CallInProgressException
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.domain.model.NetworkErrorException
import com.rumosoft.commons.domain.model.NoMoreResultsException
import com.rumosoft.commons.infrastructure.Resource
import timber.log.Timber
import javax.inject.Inject

private const val LIMIT = 20

class SearchRepositoryImpl @Inject constructor(
    private val network: CharactersNetwork,
) : SearchRepository {
    private var maxPages = Long.MAX_VALUE
    private var currentPage = 1
    private var isRequestInProgress = false

    private var currentCharacters: List<Character> = emptyList()

    override suspend fun performSearch(
        nameStartsWith: String,
        fromStart: Boolean
    ): Resource<List<Character>?> {
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

    override suspend fun getThumbnail(comicId: Int): Resource<String> {
        return network.getComicThumbnail(comicId)
    }

    private suspend fun performNetworkSearch(
        nameStartsWith: String,
        page: Int
    ): Resource<List<Character>?> {
        isRequestInProgress = true
        val offset = (page - 1) * LIMIT
        val networkResult = network.searchHeroes(offset, LIMIT, nameStartsWith)
        isRequestInProgress = false
        return if (currentPage <= maxPages && networkResult is Resource.Success) {
            Resource.Success(parseNetworkResponse(networkResult.data.characters))
        } else
            Resource.Error(NetworkErrorException("Network Error"))
    }

    private fun parseNetworkResponse(characters: List<Character>?): List<Character> {
        currentCharacters = if (currentPage > 1) {
            val newList = currentCharacters.toMutableList()
            characters?.let { newList.addAll(it) }
            newList.toList()
        } else {
            characters?.toList() ?: emptyList()
        }
        return currentCharacters
    }
}
