package com.rumosoft.comics.domain.usecase

import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource

interface GetComicDetailsUseCase {
    suspend operator fun invoke(comicId: Int): Resource<Comic>
}
