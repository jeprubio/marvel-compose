package com.rumosoft.marvelapi.data.network

import com.rumosoft.libraryTests.FileReader
import com.rumosoft.libraryTests.TestCoroutineExtension
import com.rumosoft.marvelapi.data.network.apimodels.HeroDto
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

@ExtendWith(TestCoroutineExtension::class)
internal class CharactersNetworkImplIntegrationTest {
    private val mockWebServer = MockWebServer()
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private lateinit var charactersNetwork: CharactersNetwork
    private val comicId = 1
    private val heroId = 0L
    private val offset = 0
    private val limit = 20

    @BeforeEach
    fun setUp() {
        mockWebServer.start()
        val marvelService: MarvelService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MarvelService::class.java)
        charactersNetwork = CharactersNetworkImpl(marvelService)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Successful response performing search returns Success`() =
        runTest {
            `given a response is returned when getHeroes gets called on the service`()

            val response = `when getHeroes gets called on the network`()

            `then the response should be of type Success`(response)
            `then there should be one element in the returned data`(response)
            `then the request path should be the heroes list path`()
        }

    @Test
    fun `Successful response performing getHeroDetails returns Success`() =
        runTest {
            `given a response is returned when searchHero gets called on the service`()

            val response = `when getHeroDetails gets called on the network`()

            `then the hero details response should be of type Success`(response)
            `then the request path should be the hero details path`()
        }

    @Test
    fun `Error performing search returns Error`() =
        runTest {
            `given an exception is thrown when getHeroes gets called on the service`()

            val response = `when getHeroes gets called on the network`()

            `then the response should be of type Error`(response)
        }

    @Test
    fun `Successful response performing getComicThumbnail returns Success`() =
        runTest {
            `given a response is returned when searchComic gets called on the service`()

            val response = `when getComicThumbnail gets called on the network`()

            `then the searchComic response should be of type Success`(response)
            `then there should be one element in the returned data from searchComic`(response)
            `then the request path should be the comic details path`()
        }

    @Test
    fun `Error performing getComicThumbnail returns Error`() =
        runTest {
            `given an exception is thrown when searchComic gets called on the service`()

            val response = `when getComicThumbnail gets called on the network`()

            `then the getComicThumbnail response should be of type Error`(response)
        }

    private fun `given a response is returned when getHeroes gets called on the service`() {
        FileReader.readFile("characters_list.json")?.also {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(it))
        }
    }

    private fun `given a response is returned when searchHero gets called on the service`() {
        FileReader.readFile("characters_list.json")?.also {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(it))
        }
    }

    private suspend fun `when getHeroDetails gets called on the network`(): Result<HeroDto> {
        return charactersNetwork.getHeroDetails(heroId)
    }

    private fun `then the hero details response should be of type Success`(response: Result<HeroDto>) {
        assertTrue(response.isSuccess)
    }

    private fun `given a response is returned when searchComic gets called on the service`() {
        FileReader.readFile("search_comic.json")?.also {
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(it))
        }
    }

    private fun `given an exception is thrown when getHeroes gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private suspend fun `when getHeroes gets called on the network`(): Result<HeroesResult> {
        return charactersNetwork.getHeroes(offset, limit)
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

    private fun `then the request path should be the heroes list path`() {
        assertEquals(
            "/v1/public/characters?offset=$offset&limit=$limit",
            mockWebServer.takeRequest(2, TimeUnit.SECONDS)?.path,
        )
    }

    private fun `then the request path should be the hero details path`() {
        assertEquals(
            "/v1/public/characters/$heroId",
            mockWebServer.takeRequest(2, TimeUnit.SECONDS)?.path,
        )
    }

    private fun `then the request path should be the comic details path`() {
        assertEquals(
            "/v1/public/comics/$comicId",
            mockWebServer.takeRequest(2, TimeUnit.SECONDS)?.path,
        )
    }
}
