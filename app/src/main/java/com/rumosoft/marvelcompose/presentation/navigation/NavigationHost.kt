package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.comics.presentation.screen.ComicsScreen
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.feature_characters.presentation.navigation.NavCharItem
import com.rumosoft.feature_characters.presentation.screen.DetailsScreen
import com.rumosoft.feature_characters.presentation.screen.HeroListScreen
import com.rumosoft.feature_characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.feature_characters.presentation.viewmodel.HeroListViewModel
import com.rumosoft.marvelcompose.presentation.screen.SplashScreen

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.SPLASH) {
        composable(Screen.SPLASH) {
            SplashScreen(navController = navController)
        }
        navigation(startDestination = NavCharItem.Characters.route, route = "chars") {
            composable(
                route = NavCharItem.Characters.route,
                deepLinks = listOf(navDeepLink { uriPattern = NavCharItem.Characters.deepLink }),
            ) { navBackStackEntry ->
                val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
                HeroListScreen(
                    viewModel = viewModel,
                ) { selectedCharacter ->
                    viewModel.resetSelectedHero()
                    navController.currentBackStackEntry?.arguments?.putParcelable(
                        "character",
                        selectedCharacter
                    )
                    navController.navigate(NavCharItem.Details.route)
                }
            }
            composable(
                route = NavCharItem.Details.route,
            ) { navBackStackEntry ->
                val character: Character? =
                    navController.previousBackStackEntry?.arguments?.getParcelable("character")
                val viewModel: DetailsViewModel = hiltViewModel(navBackStackEntry)
                character?.let {
                    viewModel.setHero(it)
                }
                DetailsScreen(
                    viewModel = viewModel,
                    onBackPressed = {
                        navController.popBackStack(
                            route = NavCharItem.Characters.route,
                            inclusive = false
                        )
                    },
                    onComicSelected = {
                        navController.navigate(NavComicItem.Comics.deepLink.toUri())
                    },
                )
            }
        }
        navigation(startDestination = NavComicItem.Comics.route, route = "cmcs") {
            composable(
                NavComicItem.Comics.route,
                deepLinks = listOf(navDeepLink { uriPattern = NavComicItem.Comics.deepLink }),
            ) { navBackStackEntry ->
                val viewModel: ComicListViewModel = hiltViewModel(navBackStackEntry)
                ComicsScreen(viewModel)
            }
        }
    }
}
