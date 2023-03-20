package com.rumosoft.characters.domain.usecase.interfaces

import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.infrastructure.Resource

interface SearchRepository {
    suspend fun performSearch(nameStartsWith: String, page: Int): Resource<List<Character>?>
    suspend fun getThumbnail(comicId: Int): Resource<String>
}
