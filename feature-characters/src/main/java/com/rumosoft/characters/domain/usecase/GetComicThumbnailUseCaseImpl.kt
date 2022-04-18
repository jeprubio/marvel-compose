package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.commons.infrastructure.Resource
import javax.inject.Inject

class GetComicThumbnailUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : GetComicThumbnailUseCase {
    override suspend operator fun invoke(comicId: Int): Resource<String> =
        repository.getThumbnail(comicId)
}
