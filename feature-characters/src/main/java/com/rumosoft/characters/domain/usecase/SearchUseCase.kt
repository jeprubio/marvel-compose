package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.model.CharactersPage
import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharactersRepository,
) {
    suspend operator fun invoke(page: Int): Result<CharactersPage> =
        repository.getCharacters(page)
}
