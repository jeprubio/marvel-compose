package com.rumosoft.characters.data.repository

import com.rumosoft.characters.data.network.CharactersNetwork
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.commons.domain.model.CallInProgressException
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.domain.model.NetworkErrorException
import com.rumosoft.commons.infrastructure.Resource
import timber.log.Timber
import javax.inject.Inject

private const val LIMIT = 20

class SearchRepositoryImpl @Inject constructor(
    private val network: CharactersNetwork,
) : SearchRepository {
    private var isRequestInProgress = false

    private var currentCharacters: List<Character> = emptyList()

    override suspend fun performSearch(
        nameStartsWith: String,
        page: Int,
    ): Resource<List<Character>?> {
        if (isRequestInProgress) {
            Timber.d("Request is in progress")
            return Resource.Error(CallInProgressException("Request is in progress"))
        }
        Timber.d("Performing Network Search")
        val networkResult = performNetworkSearch(nameStartsWith, page)
        if (networkResult is Resource.Success) {
            Timber.d("Returned results ${networkResult.data?.size}")
        }
        Timber.d("Returned page ${page - 1}")
        return networkResult
    }

    override suspend fun getThumbnail(comicId: Int): Resource<String> {
        return network.getComicThumbnail(comicId)
    }

    private suspend fun performNetworkSearch(
        nameStartsWith: String,
        page: Int,
    ): Resource<List<Character>?> {
        isRequestInProgress = true
        val offset = (page - 1) * LIMIT
        val networkResult = network.searchHeroes(offset, LIMIT, nameStartsWith)
        isRequestInProgress = false
        return if (networkResult is Resource.Success) {
            Resource.Success(parseNetworkResponse(networkResult.data.characters, page))
        } else {
            Resource.Error(NetworkErrorException("Network Error"))
        }
    }

    private fun parseNetworkResponse(
        characters: List<Character>?,
        page: Int,
    ): List<Character> {
        currentCharacters = if (page > 1) {
            val newList = currentCharacters.toMutableList()
            characters?.let { newList.addAll(it) }
            newList.toList()
        } else {
            characters?.toList() ?: emptyList()
        }
        return currentCharacters
    }
}
