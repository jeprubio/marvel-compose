package com.rumosoft.marvelapi.data.network

import com.rumosoft.libraryTests.FileReader
import com.rumosoft.libraryTests.TestCoroutineExtension
import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExtendWith(TestCoroutineExtension::class)
internal class ComicsNetworkImplIntegrationTest {
    private val mockWebServer = MockWebServer()
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()
    private val marvelService: MarvelService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarvelService::class.java)
    private val comicsNetwork: ComicsNetwork
    private val offset = 0
    private val limit = 20
    private val comicId = 123

    init {
        MockKAnnotations.init(this)
        comicsNetwork = ComicsNetworkImpl(marvelService)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
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
        FileReader.readFile("search_comics.json")?.also {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(it))
        }
    }

    private fun `given a response is returned when searchComic gets called on the service`() {
        FileReader.readFile("search_comic.json")?.also {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(it))
        }
    }

    private fun `given an exception is thrown when searchComics gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private suspend fun `when searchComics gets called on the network`(): Result<ComicsResult> {
        return comicsNetwork.searchComics(offset, limit, "")
    }

    private suspend fun `when fetchComic gets called on the network`(): Result<ComicDto> {
        return comicsNetwork.fetchComic(comicId)
    }

    private fun <T> `then the response should be of type Success`(response: Result<T>) {
        Assertions.assertTrue(response.isSuccess)
    }

    private fun `then there should be one element in the returned search data`(response: Result<ComicsResult>) {
        `then the response should be of type Success`(response)
        assertEquals(Result.success(1), response.map { it.comics!!.size })
    }

    private fun <T> `then the response should be of type Error`(response: Result<T>) {
        Assertions.assertTrue(response.isFailure)
    }
}
