package com.rumosoft.feature_characters.domain.usecase

import com.rumosoft.commons.infrastructure.Resource

interface GetComicThumbnailUseCase {
    suspend operator fun invoke(comicId: Int): Resource<String>
}
