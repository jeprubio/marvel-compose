package com.rumosoft.characters.data.repository

import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.libraryTests.TestCoroutineExtension
import com.rumosoft.marvelapi.data.network.CharactersNetwork
import com.rumosoft.marvelapi.data.network.HeroesResult
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class CharactersRepositoryImplTest {
    @MockK
    lateinit var marvelNetwork: CharactersNetwork
    private val charactersRepository: CharactersRepository
    private val comicId = 1
    private val thumbnailUrl = "thumbnailUrl"
    private val offset = 0
    private val limit = 20

    init {
        MockKAnnotations.init(this)
        charactersRepository = CharactersRepositoryImpl(marvelNetwork)
    }

    @Test
    fun `getHeroes is called when calling getCharacters in the repository`() =
        runTest {
            `given getHeroes invocation on network returns results`()

            `when getCharacters on repo gets invoked`()

            `then getHeroes gets executed on network`()
        }

    @Test
    fun `getComicThumbnail is called when calling getThumbnail in the repository`() =
        runTest {
            `given getComicThumbnail invocation on network returns results`()

            `when getThumbnail on repo gets invoked`()

            `then getComicThumbnail gets executed on network`()
        }

    private fun `given getHeroes invocation on network returns results`() {
        coEvery { marvelNetwork.getHeroes(offset, limit) } returns
            Result.success(
                HeroesResult(
                    PaginationInfo(1, 1),
                    SampleData.heroesDtoSample,
                ),
            )
    }

    private fun `given getComicThumbnail invocation on network returns results`() {
        coEvery { marvelNetwork.getComicThumbnail(comicId) } returns
            Result.success(thumbnailUrl)
    }

    private suspend fun `when getCharacters on repo gets invoked`(times: Int = 1) {
        repeat(times) {
            charactersRepository.getCharacters(1)
        }
    }

    private suspend fun `when getThumbnail on repo gets invoked`() {
        charactersRepository.getThumbnail(comicId)
    }

    private fun `then getHeroes gets executed on network`() {
        coVerify { marvelNetwork.getHeroes(offset, limit) }
    }

    private fun `then getComicThumbnail gets executed on network`() {
        coVerify { marvelNetwork.getComicThumbnail(comicId) }
    }
}
