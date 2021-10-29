package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.data.network.apimodels.ComicDataContainer
import com.rumosoft.marvelcompose.data.network.apimodels.ComicDto
import com.rumosoft.marvelcompose.data.network.apimodels.ComicResults
import com.rumosoft.marvelcompose.data.network.apimodels.HeroDto
import com.rumosoft.marvelcompose.data.network.apimodels.HeroResults
import com.rumosoft.marvelcompose.data.network.apimodels.ImageDto
import com.rumosoft.marvelcompose.data.network.apimodels.SearchData
import com.rumosoft.marvelcompose.domain.model.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class MarvelNetworkImplTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @MockK
    private lateinit var marvelService: MarvelService

    private val marvelNetwork: MarvelNetwork

    private val comicId = 1

    val offset = 0

    val limit = 20

    init {
        MockKAnnotations.init(this)
        marvelNetwork = MarvelNetworkImpl(marvelService)
    }

    @Test
    fun `Successful response performing search returns Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given a response is returned when searchHeroes gets called on the service`()

            val response = `when searchHeroes gets called on the network`()

            `then the response should be of type Success`(response)
            `then there should be one element in the returned data`(response)
        }

    @Test
    fun `Error performing search returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given an exception is thrown when searchHeroes gets called on the service`()

            val response = `when searchHeroes gets called on the network`()

            `then the response should be of type Error`(response)
        }

    @Test
    fun `Successful response performing getComicThumbnail returns Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given a response is returned when searchComic gets called on the service`()

            val response = `when getComicThumbnail gets called on the network`()

            `then the searchComic response should be of type Success`(response)
            `then there should be one element in the returned data from searchComic`(response)
        }

    @Test
    fun `Error performing getComicThumbnail returns Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given an exception is thrown when searchComic gets called on the service`()

            val response = `when getComicThumbnail gets called on the network`()

            `then the getComicThumbnail response should be of type Error`(response)
        }

    private fun `given a response is returned when searchHeroes gets called on the service`() {
        coEvery { marvelService.searchHeroes(offset = offset, limit = limit) } returns
            HeroResults(
                data = SearchData(
                    offset = 0,
                    limit = limit,
                    total = 1,
                    count = 1,
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

    private fun `given a response is returned when searchComic gets called on the service`() {
        coEvery { marvelService.searchComic(comicId) } returns
            ComicResults(
                data = ComicDataContainer(
                    results = listOf(
                        ComicDto(
                            title = "title",
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

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        coEvery { marvelService.searchComic(any()) } throws Exception()
    }

    private suspend fun `when searchHeroes gets called on the network`(): Resource<HeroesResult> {
        return marvelNetwork.searchHeroes(offset, limit, "")
    }

    private suspend fun `when getComicThumbnail gets called on the network`(): Resource<String> {
        return marvelNetwork.getComicThumbnail(comicId)
    }

    private fun `then the response should be of type Success`(response: Resource<HeroesResult>) {
        Assertions.assertTrue(response is Resource.Success)
    }

    private fun `then the searchComic response should be of type Success`(response: Resource<String>) {
        Assertions.assertTrue(response is Resource.Success)
    }

    private fun `then there should be one element in the returned data`(response: Resource<HeroesResult>) {
        val data = when (response) {
            is Resource.Success -> response.data
            else -> null
        }
        Assertions.assertEquals(1, data!!.heroes!!.size)
    }

    private fun `then there should be one element in the returned data from searchComic`(response: Resource<String>) {
        val data = when (response) {
            is Resource.Success -> response.data
            else -> null
        }
        Assertions.assertEquals("path.extension", data)
    }

    private fun `then the response should be of type Error`(response: Resource<HeroesResult>) {
        Assertions.assertTrue(response is Resource.Error)
    }

    private fun `then the getComicThumbnail response should be of type Error`(response: Resource<String>) {
        Assertions.assertTrue(response is Resource.Error)
    }
}
