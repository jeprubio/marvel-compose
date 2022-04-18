package com.rumosoft.commons.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Character(
    val name: String,
    val description: String,
    val thumbnail: String,
    val links: List<Link>? = null,
    val comics: List<ComicSummary>? = null,
) : Parcelable

@Keep
@Parcelize
data class Link(
    val type: String,
    val url: String,
) : Parcelable
