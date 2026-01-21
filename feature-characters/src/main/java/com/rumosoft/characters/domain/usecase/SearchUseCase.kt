package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharactersRepository,
) {
    suspend operator fun invoke(page: Int): Result<List<Character>> =
        repository.getCharacters(page)
}
