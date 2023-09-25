package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(nameStart: String, page: Int): Result<List<Character>> =
        repository.performSearch(nameStart, page)
}
