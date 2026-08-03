package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.model.ComicsPage
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import javax.inject.Inject

class GetComicsUseCase @Inject constructor(
    private val repository: ComicsRepository,
) {
    suspend operator fun invoke(page: Int): Result<ComicsPage> =
        repository.getComics(page)
}
