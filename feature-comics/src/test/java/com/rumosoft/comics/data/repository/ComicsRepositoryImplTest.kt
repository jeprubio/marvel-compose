package com.rumosoft.comics.data.repository

import com.rumosoft.comics.data.network.ComicsNetwork
import com.rumosoft.comics.data.network.ComicsResult
import com.rumosoft.comics.data.network.PaginationInfo
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.library_tests.TestCoroutineExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
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
    fun `searchComics is called twice when calling performSearch twice in the repository`() =
        runTest {
            `given searchComics invocation on network returns results`()
            `given searchComics invocation on network returns results for the second page`()

            `when performSearch on repo gets invoked`(2)

            `then searchComics gets executed twice on network`()
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
            Resource.Success(
                ComicsResult(
                    PaginationInfo(1, 1),
                    SampleData.comicsSample
                )
            )
    }

    private fun `given searchComics invocation on network returns results for the second page`() {
        coEvery { comicsNetwork.searchComics(offset + limit, limit, "") } returns
            Resource.Success(
                ComicsResult(
                    PaginationInfo(1, 1),
                    SampleData.comicsSample
                )
            )
    }

    private fun `given fetchComic invocation on network returns results`() {
        coEvery { comicsNetwork.fetchComic(comicId) } returns
            Resource.Success(
                SampleData.comicsSample.first()
            )
    }

    private suspend fun `when performSearch on repo gets invoked`(times: Int = 1) {
        for (i in 1..times) {
            comicsRepository.performSearch("")
        }
    }

    private suspend fun `when getDetails on repo gets invoked`() {
        comicsRepository.getDetails(comicId)
    }

    private fun `then searchComics gets executed on network`() {
        coVerify { comicsNetwork.searchComics(offset, limit, "") }
    }

    private fun `then searchComics gets executed twice on network`() {
        coVerify { comicsNetwork.searchComics(offset, limit, "") }
        coVerify { comicsNetwork.searchComics(offset + limit, limit, "") }
    }

    private fun `then fetchComic gets executed on network`() {
        coVerify { comicsNetwork.fetchComic(comicId) }
    }
}
