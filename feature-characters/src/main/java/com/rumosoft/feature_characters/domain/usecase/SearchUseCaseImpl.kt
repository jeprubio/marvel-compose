package com.rumosoft.feature_characters.domain.usecase

import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.commons.domain.model.Hero
import com.rumosoft.feature_characters.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : SearchUseCase {
    override suspend operator fun invoke(nameStart: String, fromStart: Boolean): Resource<List<Hero>?> =
        repository.performSearch(nameStart, fromStart)
}
