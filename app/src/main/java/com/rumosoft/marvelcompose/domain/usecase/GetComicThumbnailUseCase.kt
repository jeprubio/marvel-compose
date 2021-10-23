package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.marvelcompose.domain.model.Resource

interface GetComicThumbnailUseCase {
    suspend operator fun invoke(comicId: Int): Resource<String>
}
