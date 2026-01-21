package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val repository: CharactersRepository,
) {
    suspend operator fun invoke(
        characterId: Long,
    ): Result<Character?> =
        repository.getCharacterDetails(characterId)
}
