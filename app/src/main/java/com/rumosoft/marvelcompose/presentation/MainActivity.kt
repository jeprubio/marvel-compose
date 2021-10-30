package com.rumosoft.marvelcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.presentation.screen.DetailsScreen
import com.rumosoft.marvelcompose.presentation.screen.HeroListScreen
import com.rumosoft.marvelcompose.presentation.screen.Screen
import com.rumosoft.marvelcompose.presentation.screen.SplashScreen
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.presentation.viewmodel.DetailsViewModel
import com.rumosoft.marvelcompose.presentation.viewmodel.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelComposeTheme {
                Surface(color = MarvelComposeTheme.colors.background) {
                    MarvelApp()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun MarvelApp() {
    val navController = rememberNavController()
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
            val hero: Hero? = navController.previousBackStackEntry?.arguments?.getParcelable<Hero>("hero")
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
}
