package com.rumosoft.marvelcompose.presentation.navigation

sealed class Screen(val route: String) {
    object HeroList : Screen(HERO_LIST)
    object HeroDetails : Screen(HERO_DETAILS)
    object Comics : Screen(COMICS)

    companion object {
        const val SPLASH = "splash"
        const val HERO_LIST = "heroList"
        const val HERO_DETAILS = "heroDetails"
        const val COMICS = "comics"
    }
}
