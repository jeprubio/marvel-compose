package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo

internal fun buildPaginationInfo(
    requestOffset: Int,
    apiOffset: Int?,
    apiTotal: Int?,
    fetchedCount: Int,
    limit: Int,
): PaginationInfo {
    require(limit > 0) { "limit must be greater than 0, was $limit" }
    val offset = (apiOffset ?: requestOffset).coerceAtLeast(0)
    val fetched = fetchedCount.coerceAtLeast(0)
    val total = apiTotal?.coerceAtLeast(0)
    val totalPages = if (total != null) (total + limit - 1) / limit else 0
    val hasMorePages =
        if (total != null) {
            offset + fetched < total
        } else {
            fetched >= limit
        }
    return PaginationInfo(
        current = offset / limit + 1,
        total = totalPages,
        hasMorePages = hasMorePages,
    )
}
