package com.rumosoft.marvelapi.data.network.apimodels

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HeroDto(
    val id: Long,
    val name: String?,
    val description: String? = null,
    val modified: String? = null,
    @SerialName("resourceURI")
    val resourceUri: String? = null,
    val urls: List<UrlDto>? = null,
    val thumbnail: ImageDto? = null,
    val comics: ComicsDto? = null,
)

@Keep
@Serializable
data class UrlDto(
    val type: String? = null,
    val url: String? = null,
)

@Keep
@Serializable
data class ComicsDto(
    val available: Int?,
    val returned: Int?,
    @SerialName("collectionURI")
    val collectionUri: String? = null,
    val items: List<ComicSummaryDto>? = null,
)

@Keep
@Serializable
data class ComicSummaryDto(
    @SerialName("resourceURI")
    val resourceUri: String? = null,
    val name: String? = null,
)
