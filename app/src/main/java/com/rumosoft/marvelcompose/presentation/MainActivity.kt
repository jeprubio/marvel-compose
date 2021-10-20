package com.rumosoft.marvelcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rumosoft.marvelcompose.presentation.screen.HeroListScreen
import com.rumosoft.marvelcompose.presentation.screen.Screen
import com.rumosoft.marvelcompose.presentation.screen.SplashScreen
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.presentation.viewmodel.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MarvelApp()
                }
            }
        }
    }
}

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.SPLASH_SCREEN) {
        composable(Screen.SPLASH_SCREEN) {
            SplashScreen(navController)
        }
        composable(Screen.HERO_LIST_SCREEN) { navBackStackEntry ->
            val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
            HeroListScreen(viewModel)
        }
    }
}
