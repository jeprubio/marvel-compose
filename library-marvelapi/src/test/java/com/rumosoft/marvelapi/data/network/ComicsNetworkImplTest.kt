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
    fun `getComics returns hasMorePages false when total is null and the page is partial`() =
        runTest {
            `given a response where total is null`()

            val response = `when getComics gets called on the network`()

            `then hasMorePages should be false`(response)
        }

    @Test
    fun `getComics returns hasMorePages true when total is null and the page is full`() =
        runTest {
            `given a response where total is null and the page is full`()

            val response = `when getComics gets called on the network`()

            `then hasMorePages should be true`(response)
        }

    @Test
    fun `getComics returns 1-based current page and ceiling total pages`() =
        runTest {
            `given a response with total not divisible by limit`()

            val response = `when getComics gets called on the network`()

            assertTrue(response.isSuccess)
            val paginationInfo = response.getOrThrow().paginationInfo
            assertEquals(1, paginationInfo.current)
            assertEquals(2, paginationInfo.total)
            assertEquals(true, paginationInfo.hasMorePages)
        }

    @Test
    fun `getComics returns current page 2 for second page offset`() =
        runTest {
            `given a response for the second page`()

            val response = comicsNetwork.getComics(20, limit)

            assertTrue(response.isSuccess)
            val paginationInfo = response.getOrThrow().paginationInfo
            assertEquals(2, paginationInfo.current)
            assertEquals(3, paginationInfo.total)
            assertEquals(true, paginationInfo.hasMorePages)
        }

    @Test
    fun `getComics falls back to requested offset when api offset is null`() =
        runTest {
            `given a response with null api offset`()

            val response = comicsNetwork.getComics(20, limit)

            assertTrue(response.isSuccess)
            assertEquals(2, response.getOrThrow().paginationInfo.current)
        }

    @Test
    fun `getComics with zero limit returns Error`() =
        runTest {
            `given a response is returned when getComics gets called with zero limit`()

            val response = comicsNetwork.getComics(0, 0)

            `then the response should be of type Error`(response)
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

    private fun `given a response where total is null and the page is full`() {
        coEvery { marvelService.getComics(offset = offset, limit = limit) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = 0,
                    limit = limit,
                    total = null,
                    count = limit,
                    results = (1..limit).map {
                        ComicDto(id = it, title = "Comic$it", thumbnail = ImageDto("path", "jpg"))
                    },
                ),
            )
    }

    private fun `given a response with total not divisible by limit`() {
        coEvery { marvelService.getComics(offset = offset, limit = limit) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = 0,
                    limit = limit,
                    total = 21,
                    count = limit,
                    results = (1..limit).map {
                        ComicDto(id = it, title = "Comic$it", thumbnail = ImageDto("path", "jpg"))
                    },
                ),
            )
    }

    private fun `given a response for the second page`() {
        coEvery { marvelService.getComics(offset = 20, limit = limit) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = 20,
                    limit = limit,
                    total = 45,
                    count = limit,
                    results = (21..40).map {
                        ComicDto(id = it, title = "Comic$it", thumbnail = ImageDto("path", "jpg"))
                    },
                ),
            )
    }

    private fun `given a response with null api offset`() {
        coEvery { marvelService.getComics(offset = 20, limit = limit) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = null,
                    limit = limit,
                    total = 45,
                    count = limit,
                    results = (21..40).map {
                        ComicDto(id = it, title = "Comic$it", thumbnail = ImageDto("path", "jpg"))
                    },
                ),
            )
    }

    private fun `given a response is returned when getComics gets called with zero limit`() {
        coEvery { marvelService.getComics(offset = 0, limit = 0) } returns
            ComicResults(
                data = ComicDataContainer(
                    offset = 0,
                    limit = 0,
                    total = 1,
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
