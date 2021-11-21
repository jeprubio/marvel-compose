package com.rumosoft.commons.data.network.apimodels

import com.rumosoft.commons.domain.model.Comic

data class ComicDto(
    val id: Int? = null,
    val digitalId: Int? = null,
    val title: String?,
    val description: String? = null,
    val thumbnail: ImageDto? = null,
)

fun ComicDto.getThumbnail(): String? {
    return thumbnail?.path?.takeIf { "image_not_available" !in it }?.let {
        "${thumbnail.path}.${thumbnail.extension}"
    }
}

fun ComicDto.toComic(): Comic {
    return Comic(
        name = title.orEmpty(),
        url = "",
        thumbnail = thumbnail?.toThumbnailUrl().orEmpty()
    )
}
