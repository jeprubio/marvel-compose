package com.rumosoft.comics.presentation.navigation

sealed class ComicsScreens(val route: String) {
    object Comics : ComicsScreens(COMICS)

    companion object {
        const val COMICS = "comics"
    }
}
