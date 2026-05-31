package com.rumosoft.characters.data.repository

import com.rumosoft.characters.data.mappers.toHero
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import com.rumosoft.characters.domain.model.RequestInProgressException
import com.rumosoft.marvelapi.data.network.CharactersNetwork
import kotlinx.coroutines.sync.Mutex
import timber.log.Timber
import javax.inject.Inject

const val CHARACTERS_LIMIT = 20

class CharactersRepositoryImpl @Inject constructor(
    private val network: CharactersNetwork,
) : CharactersRepository {
    private val mutex = Mutex()

    override suspend fun getCharacters(
        page: Int,
    ): Result<List<Character>> {
        if (!mutex.tryLock()) {
            Timber.d("Request is in progress")
            return Result.failure(RequestInProgressException("Request is in progress"))
        }
        return try {
            Timber.d("Fetching characters")
            val networkResult = performNetworkFetch(page)
            if (networkResult.isSuccess) {
                Timber.d("Returned results")
            }
            Timber.d("Returned page ${page - 1}")
            networkResult
        } finally {
            mutex.unlock()
        }
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
        val offset = (page - 1) * CHARACTERS_LIMIT
        val networkResult = network.getHeroes(offset, CHARACTERS_LIMIT)
        return networkResult.map { result ->
            result.characters?.map { it.toHero() } ?: emptyList()
        }
    }
}
