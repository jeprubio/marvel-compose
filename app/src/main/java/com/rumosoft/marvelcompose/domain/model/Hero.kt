package com.rumosoft.marvelcompose.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Hero(
    val name: String,
) : Parcelable
