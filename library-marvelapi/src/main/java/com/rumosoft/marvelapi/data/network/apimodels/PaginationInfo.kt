package com.rumosoft.marvelapi.data.network.apimodels

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PaginationInfo(
    val current: Int,
    val total: Int,
    val hasMorePages: Boolean = true,
)

