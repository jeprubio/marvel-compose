package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.feature_characters.domain.model.Hero
import com.rumosoft.feature_characters.domain.model.Resource

interface SearchUseCase {
    suspend operator fun invoke(nameStart: String, fromStart: Boolean): Resource<List<Hero>?>
}
