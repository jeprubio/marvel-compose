package com.rumosoft.comics.data.repository

import com.rumosoft.comics.CoroutineTest
import com.rumosoft.comics.data.network.ComicsNetwork
import com.rumosoft.comics.data.network.ComicsResult
import com.rumosoft.comics.data.network.PaginationInfo
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.commons.infrastructure.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class ComicsRepositoryImplTest : CoroutineTest {

    override var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    override var testScope: TestCoroutineScope = TestCoroutineScope(dispatcher)

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
        testScope.runBlockingTest {
            `given searchComics invocation on network returns results`()

            `when performSearch on repo gets invoked`()

            `then searchComics gets executed on nework`()
        }

    @Test
    fun `searchComic is called when calling getDetails in the repository`() =
        testScope.runBlockingTest {
            `given fetchComic invocation on network returns results`()

            `when getDetails on repo gets invoked`()

            `then fetchComic gets executed on nework`()
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

    private fun `given fetchComic invocation on network returns results`() {
        coEvery { comicsNetwork.fetchComic(comicId) } returns
            Resource.Success(
                SampleData.comicsSample.first()
            )
    }

    private suspend fun `when performSearch on repo gets invoked`() {
        comicsRepository.performSearch("")
    }

    private suspend fun `when getDetails on repo gets invoked`() {
        comicsRepository.getDetails(comicId)
    }

    private fun `then searchComics gets executed on nework`() {
        coVerify { comicsNetwork.searchComics(offset, limit, "") }
    }

    private fun `then fetchComic gets executed on nework`() {
        coVerify { comicsNetwork.fetchComic(comicId) }
    }
}
