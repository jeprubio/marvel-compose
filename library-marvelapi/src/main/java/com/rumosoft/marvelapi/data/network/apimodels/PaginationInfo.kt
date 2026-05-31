package com.rumosoft.marvelapi.data.network.apimodels

import androidx.annotation.Keep

@Keep
data class PaginationInfo(
    var current: Int,
    var total: Int,
)
