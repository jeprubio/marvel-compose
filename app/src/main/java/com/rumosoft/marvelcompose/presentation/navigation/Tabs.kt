package com.rumosoft.marvelcompose.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.feature_characters.presentation.navigation.NavCharItem
import com.rumosoft.marvelcompose.R

sealed class Tabs(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    object Characters : Tabs(NavCharItem.Characters.destination, R.string.characters, R.drawable.ic_characters)
    object Comics : Tabs(NavComicItem.Comics.destination, R.string.comics, R.drawable.ic_comics)
}
