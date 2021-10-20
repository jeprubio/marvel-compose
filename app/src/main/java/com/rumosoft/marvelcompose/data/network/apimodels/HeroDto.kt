package com.rumosoft.marvelcompose.data.network.apimodels

import com.rumosoft.marvelcompose.domain.model.Hero

data class HeroDto(
    val id: Int? = null,
    val name: String?,
    val description: String? = null,
    val thumbnail: ImageDto? = null,
)

data class ImageDto(
    val path: String?,
    val extension: String?,
)

fun HeroDto.toHero(): Hero {
    return Hero(
        name = name.orEmpty(),
        description = description.orEmpty(),
        thumbnail = thumbnail?.toThumbnailUrl().orEmpty()
    )
}

fun ImageDto.toThumbnailUrl(): String =
    "$path.$extension"
