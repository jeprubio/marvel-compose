package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.presentation.screen.ComicsScreen
import com.rumosoft.marvelcompose.presentation.screen.DetailsScreen
import com.rumosoft.marvelcompose.presentation.screen.HeroListScreen
import com.rumosoft.marvelcompose.presentation.screen.SplashScreen
import com.rumosoft.marvelcompose.presentation.viewmodel.DetailsViewModel
import com.rumosoft.marvelcompose.presentation.viewmodel.HeroListViewModel

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.SPLASH) {
        composable(Screen.SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(Screen.HERO_LIST) { navBackStackEntry ->
            val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
            HeroListScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
        composable(Screen.HERO_DETAILS) { navBackStackEntry ->
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
        composable(Screen.COMICS) {
            ComicsScreen()
        }
    }
}
