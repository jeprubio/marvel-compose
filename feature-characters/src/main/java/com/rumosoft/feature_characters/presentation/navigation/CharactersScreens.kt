package com.rumosoft.feature_characters.presentation.navigation

sealed class CharactersScreens(val route: String) {
    object CharactersList : CharactersScreens(CHARACTERS_LIST)
    object CharacterDetails : CharactersScreens(CHARACTER_DETAILS)

    companion object {
        const val CHARACTERS_LIST = "charactersList"
        const val CHARACTER_DETAILS = "characterDetails"
    }
}
