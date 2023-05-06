package com.rumosoft.marvelcompose.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.marvelcompose.R

sealed class Tabs(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
) {
    object Characters : Tabs(
        NavCharItem.Characters.destination,
        com.rumosoft.characters.R.string.characters,
        R.drawable.ic_characters,
    )

    object Comics : Tabs(
        NavComicItem.Comics.destination,
        com.rumosoft.comics.R.string.comics,
        R.drawable.ic_comics,
    )
}
