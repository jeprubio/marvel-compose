package com.rumosoft.marvelcompose.domain.usecase.interfaces

import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource

interface SearchRepository {
    suspend fun performSearch(): Resource<List<Hero>?>
}
