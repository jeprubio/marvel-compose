package com.rumosoft.comics.domain.usecase.interfaces

import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource

interface ComicsRepository {
    suspend fun performSearch(
        nameStartsWith: String,
        fromStart: Boolean = false
    ): Resource<List<Comic>?>

    suspend fun getDetails(
        comicId: Int
    ): Resource<Comic>
}
