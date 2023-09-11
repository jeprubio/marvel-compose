package com.rumosoft.characters.domain.usecase

import com.rumosoft.commons.domain.model.Character

fun interface SearchUseCase {
    suspend operator fun invoke(nameStart: String, page: Int): Result<List<Character>>
}
