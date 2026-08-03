package com.rumosoft.characters.domain.usecase.interfaces

import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.model.CharactersPage

interface CharactersRepository {
    suspend fun getCharacters(page: Int): Result<CharactersPage>
    suspend fun getCharacterDetails(heroId: Long): Result<Character?>
    suspend fun getThumbnail(comicId: Int): Result<String>
}
