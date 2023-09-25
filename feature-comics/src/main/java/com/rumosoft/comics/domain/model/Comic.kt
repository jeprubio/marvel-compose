package com.rumosoft.comics.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
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
data class CharacterSummary(
    val items: List<CharacterUrls>,
) : Parcelable

@Keep
@Parcelize
data class CharacterUrls(
    val name: String,
    val resourceUri: String,
) : Parcelable
