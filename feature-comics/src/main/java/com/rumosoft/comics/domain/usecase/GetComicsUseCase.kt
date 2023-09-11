package com.rumosoft.comics.domain.usecase

import com.rumosoft.commons.domain.model.Comic

fun interface GetComicsUseCase {
    suspend operator fun invoke(titleStart: String, page: Int): Result<List<Comic>>
}
