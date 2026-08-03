package com.rumosoft.characters.domain.model

data class CharactersPage(
    val characters: List<Character>,
    val hasMorePages: Boolean,
)
