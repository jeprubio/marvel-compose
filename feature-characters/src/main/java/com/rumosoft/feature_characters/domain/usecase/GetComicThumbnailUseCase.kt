package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.feature_characters.domain.model.Resource

interface GetComicThumbnailUseCase {
    suspend operator fun invoke(comicId: Int): Resource<String>
}
