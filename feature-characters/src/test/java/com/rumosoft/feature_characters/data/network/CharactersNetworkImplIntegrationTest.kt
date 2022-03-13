package com.rumosoft.feature_characters.data.network

import com.google.gson.Gson
import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.commons.data.network.apimodels.ComicDataContainer
import com.rumosoft.commons.data.network.apimodels.ComicDto
import com.rumosoft.commons.data.network.apimodels.ComicResults
import com.rumosoft.commons.data.network.apimodels.HeroDto
import com.rumosoft.commons.data.network.apimodels.HeroResults
import com.rumosoft.commons.data.network.apimodels.ImageDto
import com.rumosoft.commons.data.network.apimodels.SearchData
import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.feature_characters.TestCoroutineExtension
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
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
        val result =
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

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(result)))
    }

    private fun `given a response is returned when searchComic gets called on the service`() {
        val result = ComicResults(
            data = ComicDataContainer(
                results = listOf(
                    ComicDto(
                        id = 123,
                        title = "title",
                        thumbnail = ImageDto(
                            "path",
                            "extension",
                        ),
                    )
                )
            )
        )

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(result)))
    }

    private fun `given an exception is thrown when searchHeroes gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private suspend fun `when searchHeroes gets called on the network`(): Resource<HeroesResult> {
        return charactersNetwork.searchHeroes(offset, limit, "")
    }

    private suspend fun `when getComicThumbnail gets called on the network`(): Resource<String> {
        return charactersNetwork.getComicThumbnail(comicId)
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
        Assertions.assertEquals(1, data!!.characters!!.size)
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
