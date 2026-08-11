package com.rumosoft.marvelcompose.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation3.runtime.NavKey
import com.rumosoft.components.presentation.component.isWindowCompact
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.ComicsScreen
import com.rumosoft.components.presentation.deeplinks.Screen
import com.rumosoft.components.presentation.deeplinks.resolveDeepLinkRoute
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.presentation.navigation.BottomNavigationBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationHost
import com.rumosoft.marvelcompose.presentation.navigation.NavigationRailBar
import com.rumosoft.marvelcompose.presentation.navigation.NavigationState
import com.rumosoft.marvelcompose.presentation.navigation.Navigator
import com.rumosoft.marvelcompose.presentation.navigation.Tabs
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Characters
import com.rumosoft.marvelcompose.presentation.navigation.Tabs.Comics
import com.rumosoft.marvelcompose.presentation.navigation.rememberNavigationState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        deepLinkIntent = intent

        setContent {
            val darkTheme = isSystemInDarkTheme()
            LaunchedEffect(Unit) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        android.graphics.Color.TRANSPARENT,
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { darkTheme },
                )
            }
            MarvelComposeTheme {
                Surface(
                    color = MarvelComposeTheme.colors.background,
                ) {
                    MarvelApp(deepLinkIntent = deepLinkIntent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        deepLinkIntent = intent
    }
}

@Composable
fun MarvelApp(deepLinkIntent: Intent? = null) {
    val navigationState = rememberNavigationState(
        startRoute = CharactersScreen,
        topLevelRoutes = setOf<NavKey>(CharactersScreen, ComicsScreen)
    )
    val navigator = remember { Navigator(navigationState) }

    // Handle deep links from intent. Re-runs whenever a new intent is delivered
    // (e.g. via onNewIntent), not just on first composition.
    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.let { intent ->
            handleDeepLink(intent, navigator)
        }
    }

    val navigationItems = listOf(
        Characters,
        Comics,
    )
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var topBarContent by remember { mutableStateOf<@Composable () -> Unit>({}) }

    BackHandler { onAppBack(snackBarHostState, context, scope) }
    Scaffold(
        topBar = { topBarContent() },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        bottomBar = {
            if (shouldShowBottomBar(navigationState)) {
                BottomNavigationBar(
                    navigationItems = navigationItems,
                    currentScreen = getCurrentScreen(navigationState),
                    onTabClick = { onTabClick(it, navigator) },
                )
            }
        },
    ) { innerPadding ->
        if (shouldShowNavigationRail()) {
            Row {
                NavigationRailBar(
                    navigationItems = navigationItems,
                    currentScreen = getCurrentScreen(navigationState),
                    onTabClick = { onTabClick(it, navigator) },
                )
                NavigationHost(
                    navigationState = navigationState,
                    navigator = navigator,
                    setTopBarContent = { topBarContent = it },
                    modifier = Modifier.padding(innerPadding),
                )
            }
        } else {
            NavigationHost(
                navigationState = navigationState,
                navigator = navigator,
                setTopBarContent = { topBarContent = it },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun shouldShowBottomBar(
    navigationState: NavigationState
): Boolean {
    val isOnTopLevel = navigationState.backStacks[navigationState.topLevelRoute]?.let {
        it.size == 1
    } ?: true
    return isWindowCompact() && isOnTopLevel
}

@Composable
private fun getCurrentScreen(navigationState: NavigationState): Screen {
    return when (navigationState.topLevelRoute) {
        is ComicsScreen -> ComicsScreen
        else -> CharactersScreen
    }
}

@Composable
private fun shouldShowNavigationRail() = !isWindowCompact()

private fun onTabClick(tab: Tabs, navigator: Navigator) {
    navigator.navigate(tab.screen as NavKey)
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

private fun handleDeepLink(intent: Intent, navigator: Navigator) {
    val uri = intent.data ?: return
    resolveDeepLinkRoute(uri.toString())?.let { route -> navigator.navigate(route) }
}


