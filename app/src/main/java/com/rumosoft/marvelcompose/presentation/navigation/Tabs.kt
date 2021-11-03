package com.rumosoft.marvelcompose.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.rumosoft.comics.presentation.navigation.ComicsScreens
import com.rumosoft.feature_characters.presentation.navigation.CharactersScreens
import com.rumosoft.marvelcompose.R

sealed class Tabs(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object Characters : Tabs(CharactersScreens.CHARACTERS_LIST, R.string.characters, Icons.Default.Person)
    object Comics : Tabs(ComicsScreens.COMICS, R.string.comics, Icons.Default.List)
}
