package com.rumosoft.characters.domain.usecase.interfaces

import com.rumosoft.characters.domain.model.Character

interface CharactersRepository {
    suspend fun getCharacters(page: Int): Result<List<Character>>
    suspend fun getCharacterDetails(heroId: Long): Result<Character?>
    suspend fun getThumbnail(comicId: Int): Result<String>
}
