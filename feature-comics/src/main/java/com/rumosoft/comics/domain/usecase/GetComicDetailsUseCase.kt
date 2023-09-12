package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.Comic
import javax.inject.Inject

class GetComicDetailsUseCase @Inject constructor(
    private val repository: ComicsRepository,
) {
    suspend operator fun invoke(
        comicId: Int,
    ): Result<Comic> =
        repository.getDetails(comicId)
}
