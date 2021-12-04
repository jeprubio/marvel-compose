package com.rumosoft.commons.data.network.apimodels

import com.rumosoft.commons.domain.model.Comic

data class ComicDto(
    val id: Int,
    val digitalId: Int? = null,
    val title: String?,
    val pageCount: Int? = null,
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
        id = id,
        digitalId = digitalId ?: 0,
        title = title.orEmpty(),
        pageCount = pageCount ?: 0,
        description = description.orEmpty(),
        urls = emptyList(),
        thumbnail = thumbnail?.toThumbnailUrl().orEmpty()
    )
}
