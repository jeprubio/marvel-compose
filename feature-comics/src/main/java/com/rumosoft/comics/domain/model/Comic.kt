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
    val resourceUri: String? = null,
    val thumbnail: String? = null,
) : Parcelable
