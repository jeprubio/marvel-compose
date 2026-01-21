package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import javax.inject.Inject

class GetComicThumbnailUseCase @Inject constructor(
    private val repository: CharactersRepository,
) {
    suspend operator fun invoke(comicId: Int): Result<String> =
        repository.getThumbnail(comicId)
}
