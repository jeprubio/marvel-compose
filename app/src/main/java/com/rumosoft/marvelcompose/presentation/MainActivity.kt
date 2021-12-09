package com.rumosoft.marvelcompose.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.comics.presentation.navigation.comicsNavModule
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.feature_characters.presentation.navigation.NavCharItem
import com.rumosoft.feature_characters.presentation.navigation.charactersNavModule
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.presentation.navigation.BottomNavigationBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationHost
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Characters
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Comics
import dagger.hilt.android.AndroidEntryPoint
import dev.jeziellago.compose.multinavcompose.multiNavModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            if (currentRoute(navController) in listOf(
                    NavCharItem.Characters.destination,
                    NavComicItem.Comics.route
                )
            ) {
                BottomNavigationBar(
                    navController = navController,
                    navigationItems = navigationItems,
                    onAppBack = {
                        onAppBack(scaffoldState, context, scope)
                    }
                )
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

private fun onAppBack(
    scaffoldState: ScaffoldState,
    context: Context,
    scope: CoroutineScope
) {
    if (scaffoldState.snackbarHostState.currentSnackbarData != null) {
        (context as? Activity)?.finish()
    } else {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.double_tap_to_exit))
        }
    }
}
