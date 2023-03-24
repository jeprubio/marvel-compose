package com.rumosoft.comics.domain.usecase.interfaces

import com.rumosoft.commons.domain.model.Comic

interface ComicsRepository {
    suspend fun performSearch(
        titleStartsWith: String,
        page: Int,
    ): Result<List<Comic>>

    suspend fun getDetails(
        comicId: Int,
    ): Result<Comic>
}
