package com.rumosoft.marvelcompose.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowMetricsCalculator
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.presentation.navigation.BottomNavigationBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationHost
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Characters
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Comics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val widthSizeClass = rememberWidthSizeClass()
            MarvelComposeTheme {
                Surface(color = MarvelComposeTheme.colors.background) {
                    MarvelApp(widthSizeClass)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MarvelApp(widthSizeClass: WidthSizeClass) {
    val navController = rememberNavController()
    val navigationItems = listOf(
        Characters,
        Comics,
    )
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            if (widthSizeClass == WidthSizeClass.COMPACT) {
                if (currentRoute(navController) in listOf(
                        NavCharItem.Characters.destination,
                        NavComicItem.Comics.route
                    )
                ) {
                    BottomNavigationBar(
                        navController = navController,
                        navigationItems = navigationItems,
                        onAppBack = {
                            onAppBack(snackBarHostState, context, scope)
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        NavigationHost(navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

private fun onAppBack(
    snackBarHostState: SnackbarHostState,
    context: Context,
    scope: CoroutineScope
) {
    if (snackBarHostState.currentSnackbarData != null) {
        (context as? Activity)?.finish()
    } else {
        scope.launch {
            snackBarHostState.showSnackbar(context.getString(R.string.double_tap_to_exit))
        }
    }
}

enum class WidthSizeClass { COMPACT, MEDIUM, EXPANDED }

@Composable
fun Activity.rememberWidthSizeClass(): WidthSizeClass {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 600 -> WidthSizeClass.COMPACT
        configuration.screenWidthDp < 840 -> WidthSizeClass.MEDIUM
        else -> WidthSizeClass.EXPANDED
    }
}
