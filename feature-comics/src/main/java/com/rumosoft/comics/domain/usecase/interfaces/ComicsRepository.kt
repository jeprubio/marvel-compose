package com.rumosoft.comics.domain.usecase.interfaces

import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.domain.model.ComicsPage

interface ComicsRepository {
    suspend fun getComics(page: Int): Result<ComicsPage>

    suspend fun getDetails(comicId: Int): Result<Comic>
}
