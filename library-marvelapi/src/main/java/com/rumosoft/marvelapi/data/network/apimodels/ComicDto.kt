package com.rumosoft.marvelapi.data.network.apimodels

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
