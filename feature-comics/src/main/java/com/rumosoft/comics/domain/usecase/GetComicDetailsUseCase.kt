package com.rumosoft.comics.domain.usecase

import com.rumosoft.commons.domain.model.Comic

fun interface GetComicDetailsUseCase {
    suspend operator fun invoke(comicId: Int): Result<Comic>
}
