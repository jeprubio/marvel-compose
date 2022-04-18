package com.rumosoft.commons.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ComicSummary(
    val title: String,
    val url: String,
    var thumbnail: String? = null,
) : Parcelable
