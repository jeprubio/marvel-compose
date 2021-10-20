package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.data.network.apimodels.HeroDto
import com.rumosoft.marvelcompose.data.network.apimodels.HeroResults
import com.rumosoft.marvelcompose.data.network.apimodels.ImageDto
import com.rumosoft.marvelcompose.data.network.apimodels.SearchData
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions

@ExperimentalCoroutinesApi
internal class MarvelNetworkImplTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    private lateinit var marvelService: MarvelService

    private val marvelNetwork: MarvelNetwork

    init {
        MockKAnnotations.init(this)
        marvelNetwork = MarvelNetworkImpl(marvelService)
    }

    @org.junit.jupiter.api.Test
    fun `Successful response performing search returns Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given a response is returned when searchHeroes gets called on the service`()

            val response = `when searchHeroes gets called on the view model`()

            `then the response should be of type Success`(response)
            `then there should be one element in the returned data`(response)
        }

    @org.junit.jupiter.api.Test
    fun `Error performing search returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given an exception is thrown when searchHeroes gets called on the service`()

            val response = `when searchHeroes gets called on the view model`()

            `then the response should be of type Error`(response)
        }

    private fun `given a response is returned when searchHeroes gets called on the service`() {
        coEvery { marvelService.searchHeroes() } returns
            HeroResults(
                data = SearchData(
                    results = listOf(
                        HeroDto(
                            name = "Batman",
                            thumbnail = ImageDto(
                                "path",
                                "extension",
                            ),
                        )
                    )
                )
            )
    }

    private fun `given an exception is thrown when searchHeroes gets called on the service`() {
        coEvery { marvelService.searchHeroes() } throws Exception()
    }

    private suspend fun `when searchHeroes gets called on the view model`(): Resource<List<Hero>> {
        return marvelNetwork.searchHeroes()
    }

    private fun `then the response should be of type Success`(response: Resource<List<Hero>>) {
        Assertions.assertTrue(response is Resource.Success)
    }

    private fun `then there should be one element in the returned data`(response: Resource<List<Hero>>) {
        val data = when (response) {
            is Resource.Success -> response.data
            else -> null
        }
        Assertions.assertEquals(1, data!!.size)
    }

    private fun `then the response should be of type Error`(response: Resource<List<Hero>>) {
        Assertions.assertTrue(response is Resource.Error)
    }
}
