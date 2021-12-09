package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rumosoft.feature_characters.presentation.navigation.NavCharItem
import com.rumosoft.marvelcompose.presentation.screen.SplashScreen
import dev.jeziellago.compose.multinavcompose.loadMultiNavComposables

private const val SPLASH = "splash"

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = SPLASH) {
        composable(SPLASH) {
            SplashScreen(
                onTimeout = {
                    navController.popBackStack()
                    navController.navigate(NavCharItem.Characters.route)
                }
            )
        }
        loadMultiNavComposables(navController)
    }
}
