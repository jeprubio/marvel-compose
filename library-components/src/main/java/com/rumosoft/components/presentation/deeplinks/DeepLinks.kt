package com.rumosoft.components.presentation.deeplinks

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface Screen

@Serializable
data object CharactersScreen : Screen, NavKey

@Serializable
data class CharacterDetails(val characterId: Long) : Screen, NavKey

@Serializable
data object ComicsScreen : Screen, NavKey

@Serializable
data class ComicDetails(val comicId: Int) : Screen, NavKey

const val DEEP_LINKS_BASE_PATH = "rumosoft://marvelcompose"
