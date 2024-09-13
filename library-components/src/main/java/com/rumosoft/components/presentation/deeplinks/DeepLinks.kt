package com.rumosoft.components.presentation.deeplinks

import kotlinx.serialization.Serializable

interface Screen

@Serializable
data object CharactersScreen : Screen

@Serializable
data class CharacterDetails(val characterId: Long) : Screen

@Serializable
data object ComicsScreen : Screen

@Serializable
data class ComicDetails(val comicId: Int) : Screen

const val DEEP_LINKS_BASE_PATH = "rumosoft://marvelcompose"
