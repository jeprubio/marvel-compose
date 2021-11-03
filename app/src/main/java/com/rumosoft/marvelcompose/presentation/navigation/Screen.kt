package com.rumosoft.marvelcompose.presentation.navigation

sealed class Screen(val route: String) {
    companion object {
        const val SPLASH = "splash"
    }
}
