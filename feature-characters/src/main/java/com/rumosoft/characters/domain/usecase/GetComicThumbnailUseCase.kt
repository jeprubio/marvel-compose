package com.rumosoft.characters.domain.usecase

fun interface GetComicThumbnailUseCase {
    suspend operator fun invoke(comicId: Int): Result<String>
}
