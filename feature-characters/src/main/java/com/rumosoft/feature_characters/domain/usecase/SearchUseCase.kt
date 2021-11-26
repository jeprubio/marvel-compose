package com.rumosoft.feature_characters.domain.usecase

import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.commons.domain.model.Character

interface SearchUseCase {
    suspend operator fun invoke(nameStart: String, fromStart: Boolean): Resource<List<Character>?>
}
