package com.rumosoft.characters.domain.usecase

interface GetComicThumbnailUseCase {
    suspend operator fun invoke(comicId: Int): Result<String>
}
