package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.infrastructure.Resource
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : SearchUseCase {
    override suspend operator fun invoke(nameStart: String, page: Int): Resource<List<Character>?> =
        repository.performSearch(nameStart, page)
}
