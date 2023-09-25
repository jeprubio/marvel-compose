package com.rumosoft.marvelcompose.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.presentation.navigation.BottomNavigationBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationHost
import com.rumosoft.marvelcompose.presentation.navigation.NavigationRailBar
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Characters
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Comics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MarvelComposeTheme {
                Surface(color = MarvelComposeTheme.colors.background) {
                    MarvelApp(windowSizeClass)
                }
            }
        }
    }
}

@Composable
fun MarvelApp(widthSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val navigationItems = listOf(
        Characters,
        Comics,
    )
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = {
            if (shouldShowBottomBar(widthSizeClass, navController)) {
                BottomNavigationBar(
                    navController = navController,
                    navigationItems = navigationItems,
                    onAppBack = {
                        onAppBack(snackBarHostState, context, scope)
                    },
                )
            }
        },
    ) { innerPadding ->
        if (shouldShowNavigationRail(widthSizeClass)) {
            Row {
                NavigationRailBar(
                    navController = navController,
                    navigationItems = navigationItems,
                    onAppBack = {
                        onAppBack(snackBarHostState, context, scope)
                    },
                )
                NavigationHost(navController, modifier = Modifier.padding(innerPadding))
            }
        } else {
            NavigationHost(navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun shouldShowBottomBar(
    widthSizeClass: WindowSizeClass,
    navController: NavHostController
) = isWindowCompact(widthSizeClass) && currentRoute(navController) in listOf(
    NavCharItem.Characters.destination,
    NavComicItem.Comics.route,
)

@Composable
private fun shouldShowNavigationRail(widthSizeClass: WindowSizeClass) =
    !isWindowCompact(widthSizeClass)

@Composable
private fun isWindowCompact(widthSizeClass: WindowSizeClass) =
    widthSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

private fun onAppBack(
    snackBarHostState: SnackbarHostState,
    context: Context,
    scope: CoroutineScope,
) {
    if (snackBarHostState.currentSnackbarData != null) {
        (context as? Activity)?.finish()
    } else {
        scope.launch {
            snackBarHostState.showSnackbar(context.getString(R.string.double_tap_to_exit))
        }
    }
}

