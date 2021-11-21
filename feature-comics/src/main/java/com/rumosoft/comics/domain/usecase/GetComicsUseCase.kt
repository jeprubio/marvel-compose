package com.rumosoft.comics.domain.usecase

import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource

interface GetComicsUseCase {
    suspend operator fun invoke(nameStart: String, fromStart: Boolean): Resource<List<Comic>?>
}
