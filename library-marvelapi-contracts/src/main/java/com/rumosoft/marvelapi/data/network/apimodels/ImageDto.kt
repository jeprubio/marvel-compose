package com.rumosoft.marvelapi.data.network.apimodels

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ImageDto(
    val path: String?,
    val extension: String?,
)

fun ImageDto.toThumbnailUrl(): String? {
    return path?.takeIf { "image_not_available" !in it }?.let {
        "$path.$extension"
    }
}
