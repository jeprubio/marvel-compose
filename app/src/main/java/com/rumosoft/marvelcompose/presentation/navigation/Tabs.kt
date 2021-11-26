package com.rumosoft.marvelcompose.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.feature_characters.presentation.navigation.NavCharItem
import com.rumosoft.marvelcompose.R

sealed class Tabs(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object Characters : Tabs(NavCharItem.Characters.destination, R.string.characters, Icons.Default.Person)
    object Comics : Tabs(NavComicItem.Comics.destination, R.string.comics, Icons.Default.List)
}
