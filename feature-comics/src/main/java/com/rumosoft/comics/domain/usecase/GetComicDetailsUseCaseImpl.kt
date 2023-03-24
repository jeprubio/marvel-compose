package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.Comic
import javax.inject.Inject

class GetComicDetailsUseCaseImpl @Inject constructor(
    private val repository: ComicsRepository,
) : GetComicDetailsUseCase {
    override suspend operator fun invoke(
        comicId: Int,
    ): Result<Comic> =
        repository.getDetails(comicId)
}
