package com.rumosoft.characters.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Keep
@Parcelize
@Serializable
data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: String,
    val links: List<Link> = emptyList(),
    val comics: List<ComicSummary> = emptyList(),
) : Parcelable

@Keep
@Parcelize
@Serializable
data class Link(
    val type: String,
    val url: String,
) : Parcelable

