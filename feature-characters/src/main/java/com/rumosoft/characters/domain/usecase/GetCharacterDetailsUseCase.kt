package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(
        characterId: Long,
    ): Result<Character?> =
        repository.getCharacterDetails(characterId)
}
