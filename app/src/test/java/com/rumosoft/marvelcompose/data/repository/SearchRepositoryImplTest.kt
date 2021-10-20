package com.rumosoft.marvelcompose.data.repository

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.data.network.MarvelNetwork
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

    init {
        MockKAnnotations.init(this)
        searchRepository = SearchRepositoryImpl(marvelNetwork)
    }

    @Test
    fun `searchHeroes is called when calling performSearch in the repository and no results from database`() =
        coroutineRule.testDispatcher.runBlockingTest {
            coEvery { marvelNetwork.searchHeroes() } returns
                Resource.Success(SampleData.heroesSample)

            searchRepository.performSearch()

            coVerify { marvelNetwork.searchHeroes() }
        }
}
