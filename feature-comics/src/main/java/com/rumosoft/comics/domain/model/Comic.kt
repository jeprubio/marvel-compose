package com.rumosoft.comics.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Keep
@Parcelize
@Serializable
data class Comic(
    val id: Int,
    val digitalId: Int,
    val title: String,
    val description: String,
    val pageCount: Int,
    val urls: List<String>,
    var thumbnail: String? = null,
    var characters: List<CharacterSummary> = emptyList(),
) : Parcelable

@Keep
@Parcelize
@Serializable
data class CharacterSummary(
    val items: List<CharacterUrls>,
) : Parcelable

@Keep
@Parcelize
@Serializable
data class CharacterUrls(
    val name: String,
    val resourceUri: String,
) : Parcelable
