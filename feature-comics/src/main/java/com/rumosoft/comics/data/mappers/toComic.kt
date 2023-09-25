package com.rumosoft.comics.data.mappers

import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import com.rumosoft.marvelapi.data.network.apimodels.toThumbnailUrl

fun ComicDto.toComic(): Comic {
    return Comic(
        id = id,
        digitalId = digitalId ?: 0,
        title = title.orEmpty(),
        pageCount = pageCount ?: 0,
        description = description.orEmpty(),
        urls = emptyList(),
        thumbnail = thumbnail?.toThumbnailUrl().orEmpty(),
    )
}

fun ComicDto.getThumbnail(): String? {
    return thumbnail?.path?.takeIf { "image_not_available" !in it }?.let {
        "${thumbnail!!.path}.${thumbnail!!.extension}"
    }
}
