package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
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
