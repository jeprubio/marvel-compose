package com.rumosoft.marvelapi.data.network.apimodels

import com.google.gson.annotations.SerializedName

data class HeroDto(
    val id: Int? = null,
    val name: String?,
    val description: String? = null,
    val modified: String? = null,
    @SerializedName("resourceURI")
    val resourceUri: String? = null,
    val urls: List<UrlDto>? = null,
    val thumbnail: ImageDto? = null,
    val comics: ComicsDto? = null,
)

data class UrlDto(
    val type: String? = null,
    val url: String? = null,
)

data class ComicsDto(
    val available: Int?,
    val returned: Int?,
    @SerializedName("collectionURI")
    val collectionUri: String? = null,
    val items: List<ComicSummaryDto>? = null,
)

data class ComicSummaryDto(
    @SerializedName("resourceURI")
    val resourceUri: String? = null,
    val name: String? = null,
)

