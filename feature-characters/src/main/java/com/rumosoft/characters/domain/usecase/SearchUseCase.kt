package com.rumosoft.characters.domain.usecase

import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.infrastructure.Resource

interface SearchUseCase {
    suspend operator fun invoke(nameStart: String, page: Int): Resource<List<Character>?>
}
