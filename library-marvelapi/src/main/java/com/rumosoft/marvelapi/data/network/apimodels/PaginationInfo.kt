package com.rumosoft.marvelapi.data.network.apimodels

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PaginationInfo(
    var current: Int,
    var total: Int,
)
