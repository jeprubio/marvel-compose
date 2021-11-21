package com.rumosoft.feature_characters.domain.usecase.interfaces

import com.rumosoft.commons.domain.model.Hero
import com.rumosoft.commons.infrastructure.Resource

interface SearchRepository {
    suspend fun performSearch(nameStartsWith: String, fromStart: Boolean = false): Resource<List<Hero>?>
    suspend fun getThumbnail(comicId: Int): Resource<String>
}
