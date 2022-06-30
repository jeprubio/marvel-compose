package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.characters.presentation.navigation.charactersGraph
import com.rumosoft.comics.presentation.navigation.comicsGraph
import com.rumosoft.marvelcompose.presentation.screen.RedirectScreen

private const val REDIRECT = "redirect"

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = REDIRECT, modifier = modifier) {
        composable(REDIRECT) {
            RedirectScreen(
                onLoaded = {
                    navController.popBackStack()
                    navController.navigate(NavCharItem.Characters.route)
                }
            )
        }
        charactersGraph(navController)
        comicsGraph(navController)
    }
}
