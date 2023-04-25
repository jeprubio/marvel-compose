package com.rumosoft.comics.data.network

import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.commons.data.network.apimodels.ComicDataContainer
import com.rumosoft.commons.data.network.apimodels.ComicDto
import com.rumosoft.commons.data.network.apimodels.ComicResults
import com.rumosoft.commons.data.network.apimodels.ImageDto
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestCoroutineExtension::class)
internal class ComicsNetworkImplTest {
    @MockK
    private lateinit var marvelService: MarvelService
    private val comicsNetwork: ComicsNetwork
    private val offset = 0
    private val limit = 20
    private val comicId = 123
    private val comicResults = ComicResults(
        data = ComicDataContainer(
            offset = 0,
            limit = limit,
            total = 1,
            count = 1,
            results = listOf(
                ComicDto(
                    id = 0,
                    title = "Comic",
                    thumbnail = ImageDto(
                        "path",
                        "extension",
                    ),
                ),
            ),
        ),
    )
    private val comicResultsNoData = ComicResults()

    init {
        MockKAnnotations.init(this)
        comicsNetwork = ComicsNetworkImpl(marvelService)
    }

    @Test
    fun `Successful response performing search returns Success`() =
        runTest {
            `given a response is returned when searchComics gets called on the service`()
            val response = `when searchComics gets called on the network`()

            `then the response should be of type Success`(response)
            `then there should be one element in the returned search data`(response)
        }

    @Test
    fun `Response without data performing search returns Error`() =
        runTest {
            `given a response with no data is returned when searchComics gets called on the service`()
            val response = `when searchComics gets called on the network`()

            `then the response should be of type Error`(response)
        }

    @Test
    fun `Error performing search returns Error`() =
        runTest {
            `given an exception is thrown when searchComics gets called on the service`()
            val response = `when searchComics gets called on the network`()

            `then the response should be of type Error`(response)
        }

    @Test
    fun `Successful response performing comic fetch returns Success`() =
        runTest {
            `given a response is returned when searchComic gets called on the service`()
            val response = `when fetchComic gets called on the network`()

            `then the response should be of type Success`(response)
        }

    @Test
    fun `Error performing comic fetch returns Error`() =
        runTest {
            `given an exception is thrown when searchComic gets called on the service`()
            val response = `when fetchComic gets called on the network`()

            `then the response should be of type Error`(response)
        }

    private fun `given a response is returned when searchComics gets called on the service`() {
        coEvery { marvelService.searchComics(offset = offset, limit = limit) } returns
            comicResults
    }

    private fun `given a response with no data is returned when searchComics gets called on the service`() {
        coEvery { marvelService.searchComics(offset = offset, limit = limit) } returns
            comicResultsNoData
    }

    private fun `given a response is returned when searchComic gets called on the service`() {
        coEvery { marvelService.searchComic(comicId = comicId) } returns
            comicResults
    }

    private fun `given an exception is thrown when searchComics gets called on the service`() {
        coEvery { marvelService.searchComics() } throws Exception()
    }

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        coEvery { marvelService.searchComic(comicId) } throws Exception()
    }

    private suspend fun `when searchComics gets called on the network`(): Result<ComicsResult> {
        return comicsNetwork.searchComics(offset, limit, "")
    }

    private suspend fun `when fetchComic gets called on the network`(): Result<Comic> {
        return comicsNetwork.fetchComic(comicId)
    }

    private fun <T> `then the response should be of type Success`(response: Result<T>) {
        assertTrue(response.isSuccess)
    }

    private fun `then there should be one element in the returned search data`(response: Result<ComicsResult>) {
        assertEquals(Result.success(1), response.map { it.comics!!.size })
    }

    private fun <T> `then the response should be of type Error`(response: Result<T>) {
        assertTrue(response.isFailure)
    }
}
