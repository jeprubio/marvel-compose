package com.rumosoft.marvelapi.data.network.apimodels

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ComicDto(
    val id: Int,
    val digitalId: Int? = null,
    val title: String?,
    val pageCount: Int? = null,
    val description: String? = null,
    val thumbnail: ImageDto? = null,
    @SerialName("resourceURI")
    val resourceUri: String? = null,
)

fun ComicDto.getThumbnail(): String? {
    return thumbnail?.path?.takeIf { "image_not_available" !in it }?.let {
        "${thumbnail.path}.${thumbnail.extension}"
    }
}
