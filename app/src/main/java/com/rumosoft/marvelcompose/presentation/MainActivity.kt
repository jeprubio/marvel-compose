package com.rumosoft.marvelcompose.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import com.rumosoft.components.presentation.component.isWindowCompact
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.ComicsScreen
import com.rumosoft.components.presentation.deeplinks.Screen
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.presentation.navigation.BottomNavigationBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationHost
import com.rumosoft.marvelcompose.presentation.navigation.NavigationRailBar
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Characters
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Comics
import com.rumosoft.marvelcompose.presentation.navigation.onTabClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        installSplashScreen()
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setContent {
            MarvelComposeTheme {
                Surface(
                    color = MarvelComposeTheme.colors.background,
                    modifier = Modifier.safeDrawingPadding(),
                ) {
                    MarvelApp()
                }
            }
        }
    }
}

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    val navigationItems = listOf(
        Characters,
        Comics,
    )
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    BackHandler { onAppBack(snackBarHostState, context, scope) }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = {
            if (shouldShowBottomBar(navController)) {
                BottomNavigationBar(
                    navigationItems = navigationItems,
                    currentScreen = getCurrentScreen(navController),
                    onTabClick = { onTabClick(it, navController) },
                )
            }
        },
    ) { innerPadding ->
        if (shouldShowNavigationRail()) {
            Row {
                NavigationRailBar(
                    navigationItems = navigationItems,
                    currentScreen = getCurrentScreen(navController),
                    onTabClick = { onTabClick(it, navController) },
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
    navController: NavHostController
): Boolean {
    println("isWindowCompact: ${isWindowCompact()}")
    println("currentRoute: ${currentRoute(navController)}")
    return isWindowCompact() && (
            currentRoute(navController)?.contains("CharactersScreen") == true ||
                    currentRoute(navController)?.contains("ComicsScreen") == true)
}

@Composable
private fun getCurrentScreen(navController: NavHostController): Screen {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route
    return when {
        route?.contains("CharactersScreen") == true -> CharactersScreen
        route?.contains("ComicsScreen") == true -> ComicsScreen
        else -> CharactersScreen
    }
}

@Composable
private fun shouldShowNavigationRail() = !isWindowCompact()

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

