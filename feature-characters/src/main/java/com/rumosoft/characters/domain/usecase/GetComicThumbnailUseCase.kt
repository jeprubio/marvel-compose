package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class GetComicThumbnailUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(comicId: Int): Result<String> =
        repository.getThumbnail(comicId)
}
