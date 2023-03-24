package com.rumosoft.characters.data.network

import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.libraryTests.FileReader
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
internal class CharactersNetworkImplIntegrationTest {
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
    private val charactersNetwork: CharactersNetwork
    private val comicId = 1
    private val offset = 0
    private val limit = 20

    init {
        MockKAnnotations.init(this)
        charactersNetwork = CharactersNetworkImpl(marvelService)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Successful response performing search returns Success`() =
        runTest {
            `given a response is returned when searchHeroes gets called on the service`()

            val response = `when searchHeroes gets called on the network`()

            `then the response should be of type Success`(response)
            `then there should be one element in the returned data`(response)
        }

    @Test
    fun `Error performing search returns Error`() =
        runTest {
            `given an exception is thrown when searchHeroes gets called on the service`()

            val response = `when searchHeroes gets called on the network`()

            `then the response should be of type Error`(response)
        }

    @Test
    fun `Successful response performing getComicThumbnail returns Success`() =
        runTest {
            `given a response is returned when searchComic gets called on the service`()

            val response = `when getComicThumbnail gets called on the network`()

            `then the searchComic response should be of type Success`(response)
            `then there should be one element in the returned data from searchComic`(response)
        }

    @Test
    fun `Error performing getComicThumbnail returns Error`() =
        runTest {
            `given an exception is thrown when searchComic gets called on the service`()

            val response = `when getComicThumbnail gets called on the network`()

            `then the getComicThumbnail response should be of type Error`(response)
        }

    private fun `given a response is returned when searchHeroes gets called on the service`() {
        FileReader.readFile("characters_list.json")?.also {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(it))
        }
    }

    private fun `given a response is returned when searchComic gets called on the service`() {
        FileReader.readFile("search_comic.json")?.also {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(it))
        }
    }

    private fun `given an exception is thrown when searchHeroes gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private suspend fun `when searchHeroes gets called on the network`(): Result<HeroesResult> {
        return charactersNetwork.searchHeroes(offset, limit, "")
    }

    private suspend fun `when getComicThumbnail gets called on the network`(): Result<String> {
        return charactersNetwork.getComicThumbnail(comicId)
    }

    private fun `then the response should be of type Success`(response: Result<HeroesResult>) {
        assertTrue(response.isSuccess)
    }

    private fun `then the searchComic response should be of type Success`(response: Result<String>) {
        assertTrue(response.isSuccess)
    }

    private fun `then there should be one element in the returned data`(response: Result<HeroesResult>) {
        assertEquals(Result.success(1), response.map { it.characters!!.size })
    }

    private fun `then there should be one element in the returned data from searchComic`(response: Result<String>) {
        assertEquals(Result.success("path.extension"), response)
    }

    private fun `then the response should be of type Error`(response: Result<HeroesResult>) {
        assertTrue(response.isFailure)
    }

    private fun `then the getComicThumbnail response should be of type Error`(response: Result<String>) {
        assertTrue(response.isFailure)
    }
}
