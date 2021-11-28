package com.rumosoft.comics.presentation.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import dev.jeziellago.compose.multinavcompose.NavComposableModule

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
val comicsNavModule = NavComposableModule { graph, navController ->
    graph.navigation(startDestination = NavComicItem.Comics.route, route = "cmcs") {
        composable(
            NavComicItem.Comics.route,
            deepLinks = listOf(navDeepLink { uriPattern = NavComicItem.Comics.deepLink }),
        ) { navBackStackEntry ->
            val viewModel: ComicListViewModel = hiltViewModel(navBackStackEntry)
            ComicsScreen(viewModel)
        }
    }
}
