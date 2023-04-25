package com.rumosoft.characters.data.repository

import com.rumosoft.characters.data.network.CharactersNetwork
import com.rumosoft.characters.data.network.HeroesResult
import com.rumosoft.characters.data.network.PaginationInfo
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestCoroutineExtension::class)
internal class SearchRepositoryImplTest {
    @MockK
    lateinit var marvelNetwork: CharactersNetwork
    private val searchRepository: SearchRepository
    private val comicId = 1
    private val thumbnailUrl = "thumbnailUrl"
    private val offset = 0
    private val limit = 20

    init {
        MockKAnnotations.init(this)
        searchRepository = SearchRepositoryImpl(marvelNetwork)
    }

    @Test
    fun `searchHeroes is called when calling performSearch in the repository`() =
        runTest {
            `given searchHeroes invocation on network returns results`()

            `when performSearch on repo gets invoked`()

            `then searchHeroes gets executed on network`()
        }

    @Test
    fun `getComicThumbnail is called when calling getThumbnail in the repository`() =
        runTest {
            `given getComicThumbnail invocation on network returns results`()

            `when getThumbnail on repo gets invoked`()

            `then getComicThumbnail gets executed on network`()
        }

    private fun `given searchHeroes invocation on network returns results`() {
        coEvery { marvelNetwork.searchHeroes(offset, limit, "") } returns
            Result.success(
                HeroesResult(
                    PaginationInfo(1, 1),
                    SampleData.heroesSample,
                ),
            )
    }

    private fun `given getComicThumbnail invocation on network returns results`() {
        coEvery { marvelNetwork.getComicThumbnail(comicId) } returns
            Result.success(thumbnailUrl)
    }

    private suspend fun `when performSearch on repo gets invoked`(times: Int = 1) {
        repeat(times) {
            searchRepository.performSearch("", 1)
        }
    }

    private suspend fun `when getThumbnail on repo gets invoked`() {
        searchRepository.getThumbnail(comicId)
    }

    private fun `then searchHeroes gets executed on network`() {
        coVerify { marvelNetwork.searchHeroes(offset, limit, "") }
    }

    private fun `then getComicThumbnail gets executed on network`() {
        coVerify { marvelNetwork.getComicThumbnail(comicId) }
    }
}
