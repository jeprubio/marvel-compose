package com.rumosoft.marvelcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.comics.presentation.navigation.comicsNavModule
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.feature_characters.presentation.navigation.NavCharItem
import com.rumosoft.feature_characters.presentation.navigation.charactersNavModule
import com.rumosoft.marvelcompose.presentation.navigation.BottomNavigationBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationHost
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Characters
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Comics
import dagger.hilt.android.AndroidEntryPoint
import dev.jeziellago.compose.multinavcompose.multiNavModules

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        multiNavModules(charactersNavModule, comicsNavModule)

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
@ExperimentalFoundationApi
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
                    NavCharItem.Characters.destination,
                    NavComicItem.Comics.route
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
