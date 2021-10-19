package com.rumosoft.marvelcompose.presentation.screen

sealed class Screen(val route: String) {
    object HeroListScreen : Screen(HERO_LIST_SCREEN)

    companion object {
        const val SPLASH_SCREEN = "splash"
        const val HERO_LIST_SCREEN = "heroList"
    }
}
