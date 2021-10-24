package com.rumosoft.marvelcompose.domain.usecase.interfaces

import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource

interface SearchRepository {
    suspend fun performSearch(fromStart: Boolean = false): Resource<List<Hero>?>
    suspend fun getThumbnail(comicId: Int): Resource<String>
}
