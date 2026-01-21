package com.rumosoft.characters.data.repository

import com.rumosoft.characters.data.mappers.toHero
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import com.rumosoft.marvelapi.data.network.CallInProgressException
import com.rumosoft.marvelapi.data.network.CharactersNetwork
import timber.log.Timber
import javax.inject.Inject

const val CHARACTERS_LIMIT = 20

class CharactersRepositoryImpl @Inject constructor(
    private val network: CharactersNetwork,
) : CharactersRepository {
    private var isRequestInProgress = false

    override suspend fun getCharacters(
        page: Int,
    ): Result<List<Character>> {
        if (isRequestInProgress) {
            Timber.d("Request is in progress")
            return Result.failure(CallInProgressException("Request is in progress"))
        }
        Timber.d("Fetching characters")
        val networkResult = performNetworkFetch(page)
        if (networkResult.isSuccess) {
            Timber.d("Returned results")
        }
        Timber.d("Returned page ${page - 1}")
        return networkResult
    }

    override suspend fun getCharacterDetails(heroId: Long): Result<Character?> {
        val heroDetails = network.getHeroDetails(heroId)
        if (heroDetails.isSuccess) {
            Timber.d("Returned hero details")
        }
        return heroDetails.map { it?.toHero() }
    }

    override suspend fun getThumbnail(comicId: Int): Result<String> {
        return network.getComicThumbnail(comicId)
    }

    private suspend fun performNetworkFetch(
        page: Int,
    ): Result<List<Character>> {
        isRequestInProgress = true
        val offset = (page - 1) * CHARACTERS_LIMIT
        val networkResult = network.getHeroes(offset, CHARACTERS_LIMIT)
        isRequestInProgress = false
        return networkResult.map { result ->
            result.characters?.map { it.toHero() } ?: emptyList()
        }
    }
}
