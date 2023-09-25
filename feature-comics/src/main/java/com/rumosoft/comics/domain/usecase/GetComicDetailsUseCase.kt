package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import javax.inject.Inject

class GetComicDetailsUseCase @Inject constructor(
    private val repository: ComicsRepository,
) {
    suspend operator fun invoke(
        comicId: Int,
    ): Result<Comic> =
        repository.getDetails(comicId)
}
