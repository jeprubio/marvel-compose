package com.rumosoft.marvelapi.data.network.apimodels

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HeroDto(
    val id: Long,
    val name: String?,
    val description: String? = null,
    val modified: String? = null,
    @SerializedName("resourceURI")
    val resourceUri: String? = null,
    val urls: List<UrlDto>? = null,
    val thumbnail: ImageDto? = null,
    val comics: ComicsDto? = null,
)

@Keep
data class UrlDto(
    val type: String? = null,
    val url: String? = null,
)

@Keep
data class ComicsDto(
    val available: Int?,
    val returned: Int?,
    @SerializedName("collectionURI")
    val collectionUri: String? = null,
    val items: List<ComicSummaryDto>? = null,
)

@Keep
data class ComicSummaryDto(
    @SerializedName("resourceURI")
    val resourceUri: String? = null,
    val name: String? = null,
)

