package com.rumosoft.marvelapi.data.network

import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo

interface ComicsNetwork {
    suspend fun getComics(
        offset: Int,
        limit: Int,
    ): Result<ComicsResult>

    suspend fun fetchComic(comicId: Int): Result<ComicDto>
}

data class ComicsResult(val paginationInfo: PaginationInfo, val comics: List<ComicDto>?)
