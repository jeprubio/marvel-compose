package com.rumosoft.comics.data.repository

import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.libraryTests.TestCoroutineExtension
import com.rumosoft.marvelapi.data.network.ComicsNetwork
import com.rumosoft.marvelapi.data.network.ComicsResult
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class ComicsRepositoryImplTest {
    @MockK
    lateinit var comicsNetwork: ComicsNetwork
    private val comicsRepository: ComicsRepository
    private val offset = 0
    private val limit = 20
    private val comicId = 123

    init {
        MockKAnnotations.init(this)
        comicsRepository = ComicsRepositoryImpl(comicsNetwork)
    }

    @Test
    fun `searchComics is called when calling performSearch in the repository`() =
        runTest {
            `given searchComics invocation on network returns results`()

            `when performSearch on repo gets invoked`()

            `then searchComics gets executed on network`()
        }

    @Test
    fun `searchComic is called when calling getDetails in the repository`() =
        runTest {
            `given fetchComic invocation on network returns results`()

            `when getDetails on repo gets invoked`()

            `then fetchComic gets executed on network`()
        }

    private fun `given searchComics invocation on network returns results`() {
        coEvery { comicsNetwork.searchComics(offset, limit, "") } returns
            Result.success(
                ComicsResult(
                    PaginationInfo(1, 1),
                    SampleData.comicsDtoSample,
                ),
            )
    }

    private fun `given fetchComic invocation on network returns results`() {
        coEvery { comicsNetwork.fetchComic(comicId) } returns
            Result.success(
                SampleData.comicsDtoSample.first(),
            )
    }

    private suspend fun `when performSearch on repo gets invoked`(times: Int = 1) {
        repeat(times) {
            comicsRepository.performSearch("", 1)
        }
    }

    private suspend fun `when getDetails on repo gets invoked`() {
        comicsRepository.getDetails(comicId)
    }

    private fun `then searchComics gets executed on network`() {
        coVerify { comicsNetwork.searchComics(offset, limit, "") }
    }

    private fun `then fetchComic gets executed on network`() {
        coVerify { comicsNetwork.fetchComic(comicId) }
    }
}
