package com.rumosoft.characters.domain.usecase.interfaces

import com.rumosoft.commons.domain.model.Character

interface SearchRepository {
    suspend fun performSearch(nameStartsWith: String, page: Int): Result<List<Character>>
    suspend fun getThumbnail(comicId: Int): Result<String>
}
