package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository

class SearchUseCaseImpl(
    private val repository: SearchRepository,
) : SearchUseCase {
    override suspend operator fun invoke(): Resource<List<Hero>?> =
        repository.performSearch()
}
