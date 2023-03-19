package com.rumosoft.comics.data.network

import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource

interface ComicsNetwork {
    suspend fun searchComics(
        offset: Int,
        limit: Int,
        titleStartsWith: String,
    ): Resource<ComicsResult>

    suspend fun fetchComic(comicId: Int): Resource<Comic>
}
