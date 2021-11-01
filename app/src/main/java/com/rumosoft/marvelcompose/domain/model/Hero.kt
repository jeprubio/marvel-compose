package com.rumosoft.marvelcompose.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Hero(
    val name: String,
    val description: String,
    val thumbnail: String,
    val links: List<Link>? = null,
    val comics: List<Comic>? = null,
) : Parcelable

@Keep
@Parcelize
data class Link(
    val type: String,
    val url: String,
) : Parcelable

@Keep
@Parcelize
data class Comic(
    val name: String,
    val url: String,
    var thumbnail: String? = null,
) : Parcelable
