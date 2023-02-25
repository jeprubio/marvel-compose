package com.rumosoft.commons.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Keep
@Parcelize
@Serializable
data class ComicSummary(
    val title: String,
    val url: String,
    var thumbnail: String? = null,
) : Parcelable
