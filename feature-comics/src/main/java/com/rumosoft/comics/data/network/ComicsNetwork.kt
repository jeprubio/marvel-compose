package com.rumosoft.comics.data.network

import com.rumosoft.commons.domain.model.Comic

interface ComicsNetwork {
    suspend fun searchComics(
        offset: Int,
        limit: Int,
        titleStartsWith: String,
    ): Result<ComicsResult>

    suspend fun fetchComic(comicId: Int): Result<Comic>
}
