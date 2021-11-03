package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.feature_characters.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class GetComicThumbnailUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : GetComicThumbnailUseCase {
    override suspend operator fun invoke(comicId: Int): Resource<String> =
        repository.getThumbnail(comicId)
}
