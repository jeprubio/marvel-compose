package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
) : SearchUseCase {
    override suspend operator fun invoke(nameStart: String, fromStart: Boolean): Resource<List<Hero>?> =
        repository.performSearch(nameStart, fromStart)
}
