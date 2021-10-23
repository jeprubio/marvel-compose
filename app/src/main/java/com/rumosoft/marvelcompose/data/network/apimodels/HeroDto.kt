package com.rumosoft.marvelcompose.data.network.apimodels

import com.google.gson.annotations.SerializedName
import com.rumosoft.marvelcompose.domain.model.Comic
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Link

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

data class ImageDto(
    val path: String?,
    val extension: String?,
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

fun HeroDto.toHero(): Hero {
    return Hero(
        name = name.orEmpty(),
        description = description.orEmpty(),
        thumbnail = thumbnail?.toThumbnailUrl().orEmpty(),
        links = urls?.map { it.toUrl() }.orEmpty(),
        comics = comics?.items?.map { it.toComic() }.orEmpty(),
    )
}

fun ImageDto.toThumbnailUrl(): String? {
    return path?.takeIf { "image_not_available" !in it }?.let {
        "$path.$extension"
    }
}

fun UrlDto.toUrl(): Link =
    Link(
        type = type.orEmpty(),
        url = url.orEmpty(),
    )

fun ComicSummaryDto.toComic(): Comic =
    Comic(
        name = name.orEmpty(),
        url = resourceUri.orEmpty(),
    )
