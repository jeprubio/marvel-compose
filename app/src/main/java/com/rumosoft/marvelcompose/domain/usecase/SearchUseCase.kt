package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource

interface SearchUseCase {
    suspend operator fun invoke(nameStart: String, fromStart: Boolean): Resource<List<Hero>?>
}
