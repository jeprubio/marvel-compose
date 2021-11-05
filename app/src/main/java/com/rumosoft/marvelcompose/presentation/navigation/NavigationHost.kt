package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.rumosoft.comics.presentation.navigation.ComicsScreens.Companion.COMICS
import com.rumosoft.comics.presentation.navigation.ComicsScreens.Companion.COMICS_ROUTE
import com.rumosoft.comics.presentation.screen.ComicsScreen
import com.rumosoft.commons.DeepLinkUri
import com.rumosoft.feature_characters.domain.model.Hero
import com.rumosoft.feature_characters.presentation.navigation.CharactersScreens.Companion.CHARACTERS_LIST
import com.rumosoft.feature_characters.presentation.navigation.CharactersScreens.Companion.CHARACTERS_ROUTE
import com.rumosoft.feature_characters.presentation.navigation.CharactersScreens.Companion.CHARACTER_DETAILS
import com.rumosoft.feature_characters.presentation.screen.DetailsScreen
import com.rumosoft.feature_characters.presentation.screen.HeroListScreen
import com.rumosoft.feature_characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.feature_characters.presentation.viewmodel.HeroListViewModel
import com.rumosoft.marvelcompose.presentation.screen.SplashScreen

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.SPLASH) {
        composable(Screen.SPLASH) {
            SplashScreen(navController = navController)
        }
        navigation(startDestination = CHARACTERS_LIST, route = CHARACTERS_ROUTE) {
            composable(
                route = CHARACTERS_LIST,
                deepLinks = listOf(navDeepLink { uriPattern = "$DeepLinkUri/characters" }),
            ) { navBackStackEntry ->
                val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
                HeroListScreen(
                    navController = navController,
                    viewModel = viewModel,
                )
            }
            composable(CHARACTER_DETAILS) { navBackStackEntry ->
                val hero: Hero? =
                    navController.previousBackStackEntry?.arguments?.getParcelable("hero")
                val viewModel: DetailsViewModel = hiltViewModel(navBackStackEntry)
                hero?.let {
                    viewModel.setHero(it)
                }
                DetailsScreen(
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
        navigation(startDestination = COMICS, route = COMICS_ROUTE) {
            composable(
                COMICS,
                deepLinks = listOf(navDeepLink { uriPattern = "$DeepLinkUri/comics" }),
            ) {
                ComicsScreen()
            }
        }
    }
}
