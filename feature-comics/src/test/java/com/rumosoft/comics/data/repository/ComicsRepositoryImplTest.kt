package com.rumosoft.comics.data.repository

import com.rumosoft.comics.MainCoroutineRule
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
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class ComicsRepositoryImplTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    lateinit var comicsNetwork: ComicsNetwork

    private val comicsRepository: ComicsRepository

    private val offset = 0

    private val limit = 20

    init {
        MockKAnnotations.init(this)
        comicsRepository = ComicsRepositoryImpl(comicsNetwork)
    }

    @Test
    fun `searchComics is called when calling performSearch in the repository`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchComics invocation on network returns results`()

            `when performSearch on repo gets invoked`()

            `then searchComics gets executed on nework`()
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

    private suspend fun `when performSearch on repo gets invoked`() {
        comicsRepository.performSearch("")
    }

    private fun `then searchComics gets executed on nework`() {
        coVerify { comicsNetwork.searchComics(offset, limit, "") }
    }
}
