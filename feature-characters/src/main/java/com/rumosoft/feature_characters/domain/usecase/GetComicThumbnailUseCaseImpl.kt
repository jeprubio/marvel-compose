package com.rumosoft.feature_characters.domain.usecase

import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.feature_characters.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class GetComicThumbnailUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : GetComicThumbnailUseCase {
    override suspend operator fun invoke(comicId: Int): Resource<String> =
        repository.getThumbnail(comicId)
}
