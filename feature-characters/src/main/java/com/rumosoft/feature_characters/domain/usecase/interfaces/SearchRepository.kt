package com.rumosoft.marvelcompose.domain.usecase.interfaces

import com.rumosoft.feature_characters.domain.model.Hero
import com.rumosoft.feature_characters.domain.model.Resource

interface SearchRepository {
    suspend fun performSearch(nameStartsWith: String, fromStart: Boolean = false): Resource<List<Hero>?>
    suspend fun getThumbnail(comicId: Int): Resource<String>
}
