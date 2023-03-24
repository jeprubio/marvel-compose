package com.rumosoft.comics.domain.usecase

import com.rumosoft.commons.domain.model.Comic

interface GetComicDetailsUseCase {
    suspend operator fun invoke(comicId: Int): Result<Comic>
}
