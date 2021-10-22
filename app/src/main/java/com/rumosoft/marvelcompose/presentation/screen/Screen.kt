package com.rumosoft.marvelcompose.presentation.screen

sealed class Screen(val route: String) {
    object HeroList : Screen(HERO_LIST)
    object HeroDetails : Screen(HERO_DETAILS)

    companion object {
        const val SPLASH = "splash"
        const val HERO_LIST = "heroList"
        const val HERO_DETAILS = "heroDetails"
    }
}
