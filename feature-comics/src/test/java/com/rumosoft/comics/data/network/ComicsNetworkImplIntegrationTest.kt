package com.rumosoft.comics.data.network

import com.google.gson.Gson
import com.rumosoft.comics.TestCoroutineExtension
import com.rumosoft.commons.data.network.MarvelService
import com.rumosoft.commons.data.network.apimodels.ComicDataContainer
import com.rumosoft.commons.data.network.apimodels.ComicDto
import com.rumosoft.commons.data.network.apimodels.ComicResults
import com.rumosoft.commons.data.network.apimodels.ImageDto
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource
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
                )
            )
        )
    )

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
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(comicResults)))
    }

    private fun `given a response is returned when searchComic gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(Gson().toJson(comicResults)))
    }

    private fun `given an exception is thrown when searchComics gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private fun `given an exception is thrown when searchComic gets called on the service`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
    }

    private suspend fun `when searchComics gets called on the network`(): Resource<ComicsResult> {
        return comicsNetwork.searchComics(offset, limit, "")
    }

    private suspend fun `when fetchComic gets called on the network`(): Resource<Comic> {
        return comicsNetwork.fetchComic(comicId)
    }

    private fun <T> `then the response should be of type Success`(response: Resource<T>) {
        Assertions.assertTrue(response is Resource.Success)
    }

    private fun `then there should be one element in the returned search data`(response: Resource<ComicsResult>) {
        val data = when (response) {
            is Resource.Success -> response.data
            else -> null
        }
        Assertions.assertEquals(1, data!!.comics!!.size)
    }

    private fun <T> `then the response should be of type Error`(response: Resource<T>) {
        Assertions.assertTrue(response is Resource.Error)
    }
}
