package com.rumosoft.marvelcompose.data

import com.rumosoft.characters.data.repository.CHARACTERS_SEARCH_LIMIT
import com.rumosoft.marvelapi.data.network.ComicsNetwork
import com.rumosoft.marvelapi.data.network.ComicsResult
import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import com.rumosoft.marvelapi.data.network.apimodels.PaginationInfo
import javax.inject.Inject

class FakeComicsNetwork @Inject constructor(): ComicsNetwork {
    override suspend fun searchComics(
        offset: Int,
        limit: Int,
        titleStartsWith: String
    ): Result<ComicsResult> {
        val comicsResult = ComicsResult(
            paginationInfo = PaginationInfo(
                current = 0,
                total = 0,
            ),
            comics = (offset..offset + CHARACTERS_SEARCH_LIMIT).map {
                ComicDto(
                    id = it,
                    title = "comic $it",
                    description = "description $it",
                    thumbnail = null,
                )
            }
        )
        return Result.success(comicsResult)
    }

    override suspend fun fetchComic(comicId: Int): Result<ComicDto> {
        val comic = ComicDto(
            id = comicId,
            title = "comic $comicId",
            description = "description $comicId",
            thumbnail = null,
        )
        return Result.success(comic)
    }
}
