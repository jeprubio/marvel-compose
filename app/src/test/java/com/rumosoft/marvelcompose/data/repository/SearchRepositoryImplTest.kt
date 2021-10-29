package com.rumosoft.marvelcompose.data.repository

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.data.network.HeroesResult
import com.rumosoft.marvelcompose.data.network.MarvelNetwork
import com.rumosoft.marvelcompose.data.network.PaginationInfo
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class SearchRepositoryImplTest : TestCase() {

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var marvelNetwork: MarvelNetwork

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
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchHeroes invocation on network returns results`()

            `when performSearch on repo gets invoked`()

            `then searchHeroes gets executed on nework`()
        }

    @Test
    fun `getComicThumbnail is called when calling getThumbnail in the repository`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given getComicThumbnail invocation on network returns results`()

            `when getThumbnail on repo gets invoked`()

            `then getComicThumbnail gets executed on network`()
        }

    private fun `given searchHeroes invocation on network returns results`() {
        coEvery { marvelNetwork.searchHeroes(offset, limit, "") } returns
            Resource.Success(
                HeroesResult(
                    PaginationInfo(1, 1),
                    SampleData.heroesSample
                )
            )
    }

    private fun `given getComicThumbnail invocation on network returns results`() {
        coEvery { marvelNetwork.getComicThumbnail(comicId) } returns
            Resource.Success(thumbnailUrl)
    }

    private suspend fun `when performSearch on repo gets invoked`() {
        searchRepository.performSearch("")
    }

    private suspend fun `when getThumbnail on repo gets invoked`() {
        searchRepository.getThumbnail(comicId)
    }

    private fun `then searchHeroes gets executed on nework`() {
        coVerify { marvelNetwork.searchHeroes(offset, limit, "") }
    }

    private fun `then getComicThumbnail gets executed on network`() {
        coVerify { marvelNetwork.getComicThumbnail(comicId) }
    }
}
