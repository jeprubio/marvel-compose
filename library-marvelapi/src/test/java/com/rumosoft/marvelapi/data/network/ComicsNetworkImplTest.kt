package com.rumosoft.marvelapi.data.network

import com.rumosoft.libraryTests.TestCoroutineExtension
import com.rumosoft.marvelapi.data.network.apimodels.ComicDataContainer
import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import com.rumosoft.marvelapi.data.network.apimodels.ComicResults
import com.rumosoft.marvelapi.data.network.apimodels.ImageDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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
    fun `Successful response performing getComics returns Success`() =
        runTest {
            `given a response is returned when getComics gets called on the service`()
            val response = `when getComics gets called on the network`()

            `then the response should be of type Success`(response)
            `then there should be one element in the returned data`(response)
        }

    @Test
    fun `Response without data performing getComics returns Error`() =
        runTest {
            `given a response with no data is returned when getComics gets called on the service`()
            val response = `when getComics gets called on the network`()

            `then the response should be of type Error`(response)
        }

    @Test
    fun `Error performing getComics returns Error`() =
        runTest {
            `given an exception is thrown when getComics gets called on the service`()
            val response = `when getComics gets called on the network`()

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

    private fun `given a response is returned when getComics gets called on the service`() {
        coEvery { marvelService.getComics(offset = offset, limit = limit) } returns
            comicResults
    }

    private fun `given a response with no data is returned when getComics gets called on the service`() {
        coEvery { marvelService.getComics(offset = offset, limit = limit) } returns
            comicResultsNoData
    }

    private fun `given a response is returned when searchComic gets called on the service`() {
        coEvery { marvelService.searchComic(comicId = comicId) } returns
            comicResults
    }

    @Test
    fun `getComics returns hasMorePages false when offset plus result count equals total`() =
        runTest {
            `given a response where offset plus count equals total`()

            val response = `when getComics gets called on the network`()

            `then hasMorePages should be false`(response)
        }

    @Test
    fun `getComics returns hasMorePages true when more pages remain`() =
        runTest {
            `given a response where offset plus count is less than total`()

            val response = `when getComics gets called on the network`()

            `then hasMorePages should be true`(response)
        }

    @Test
    fun `getComics returns hasMorePages true when total is null`() =
        runTest {
            `given a response where total is null`()

            val response = `when getComics gets called on the network`()

            `then hasMorePages should be true`(response)
        }

    private fun `given an exception is thrown when getComics gets called on the service`() {
        coEvery { marvelService.getComics() } throws Exception()
    }

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        coEvery { marvelService.searchComic(comicId) } throws Exception()
    }

    private fun `given a response where offset plus count equals total`() {
        coEvery { marvelService.getComics(offset = offset, limit = limit) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = 0,
                    limit = limit,
                    total = 1,
                    count = 1,
                    results = listOf(
                        ComicDto(id = 0, title = "Comic", thumbnail = ImageDto("path", "jpg")),
                    ),
                ),
            )
    }

    private fun `given a response where offset plus count is less than total`() {
        coEvery { marvelService.getComics(offset = offset, limit = limit) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = 0,
                    limit = limit,
                    total = 100,
                    count = limit,
                    results = (1..limit).map {
                        ComicDto(id = it, title = "Comic$it", thumbnail = ImageDto("path", "jpg"))
                    },
                ),
            )
    }

    private fun `given a response where total is null`() {
        coEvery { marvelService.getComics(offset = offset, limit = limit) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = 0,
                    limit = limit,
                    total = null,
                    count = 1,
                    results = listOf(
                        ComicDto(id = 0, title = "Comic", thumbnail = ImageDto("path", "jpg")),
                    ),
                ),
            )
    }

    private suspend fun `when getComics gets called on the network`(): Result<ComicsResult> {
        return comicsNetwork.getComics(offset, limit)
    }

    private suspend fun `when fetchComic gets called on the network`(): Result<ComicDto> {
        return comicsNetwork.fetchComic(comicId)
    }

    private fun <T> `then the response should be of type Success`(response: Result<T>) {
        assertTrue(response.isSuccess)
    }

    private fun `then there should be one element in the returned data`(response: Result<ComicsResult>) {
        assertEquals(Result.success(1), response.map { it.comics!!.size })
    }

    private fun <T> `then the response should be of type Error`(response: Result<T>) {
        assertTrue(response.isFailure)
    }

    private fun `then hasMorePages should be false`(response: Result<ComicsResult>) {
        assertTrue(response.isSuccess)
        assertEquals(false, response.getOrThrow().paginationInfo.hasMorePages)
    }

    private fun `then hasMorePages should be true`(response: Result<ComicsResult>) {
        assertTrue(response.isSuccess)
        assertEquals(true, response.getOrThrow().paginationInfo.hasMorePages)
    }
}
