package com.rumosoft.marvelcompose.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.rumosoft.marvelcompose.R

sealed class Tabs(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object Characters : Tabs(Screen.HERO_LIST, R.string.characters, Icons.Default.Person)
    object Comics : Tabs(Screen.COMICS, R.string.comics, Icons.Default.List)

    companion object {
        const val CHARACTERS = "characters"
        const val COMICS = "comics"
    }
}
