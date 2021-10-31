package com.rumosoft.marvelcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rumosoft.marvelcompose.presentation.navigation.BottomNavigationBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationHost
import com.rumosoft.marvelcompose.presentation.navigation.Screen
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Characters
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Comics
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
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

    val navigationItems = listOf(
        Characters,
        Comics,
    )

    Scaffold(
        bottomBar = {
            if (currentRoute(navController) in listOf(
                    Screen.HERO_LIST,
                    Screen.COMICS
                )
            ) {
                BottomNavigationBar(navController, navigationItems)
            }
        },
    ) {
        NavigationHost(navController)
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
