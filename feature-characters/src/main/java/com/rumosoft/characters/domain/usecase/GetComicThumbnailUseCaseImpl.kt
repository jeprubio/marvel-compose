package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class GetComicThumbnailUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : GetComicThumbnailUseCase {
    override suspend operator fun invoke(comicId: Int): Result<String> =
        repository.getThumbnail(comicId)
}
