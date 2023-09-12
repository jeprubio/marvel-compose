package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.Comic
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(
    private val repository: ComicsRepository,
) {
    suspend operator fun invoke(
        titleStart: String,
        page: Int,
    ): Result<List<Comic>> =
        repository.performSearch(titleStart, page)
}
