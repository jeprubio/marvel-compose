package com.rumosoft.marvelcompose.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.ComicsScreen
import com.rumosoft.components.presentation.deeplinks.Screen
import com.rumosoft.marvelcompose.R

sealed class Tabs(
    val screen: Screen,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    data object Characters : Tabs(
        CharactersScreen,
        com.rumosoft.characters.R.string.characters,
        R.drawable.ic_characters,
    )

    data object Comics : Tabs(
        ComicsScreen,
        com.rumosoft.comics.R.string.comics,
        R.drawable.ic_comics,
    )
}
