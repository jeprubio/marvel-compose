package com.rumosoft.feature_characters.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.rumosoft.commons.DeepLinks
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.feature_characters.presentation.screen.DetailsScreen
import com.rumosoft.feature_characters.presentation.screen.HeroListScreen
import com.rumosoft.feature_characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.feature_characters.presentation.viewmodel.HeroListViewModel
import dev.jeziellago.compose.multinavcompose.NavComposableModule

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
val charactersNavModule = NavComposableModule { graph, navController ->
    graph.navigation(startDestination = NavCharItem.Characters.route, route = "chars") {
        composable(
            route = NavCharItem.Characters.route,
            deepLinks = listOf(navDeepLink { uriPattern = NavCharItem.Characters.deepLink }),
        ) { navBackStackEntry ->
            val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
            HeroListScreen(
                viewModel = viewModel,
            ) { selectedCharacter ->
                viewModel.resetSelectedHero()
                navController.currentBackStackEntry?.arguments?.putParcelable(
                    "character",
                    selectedCharacter
                )
                navController.navigate(NavCharItem.Details.route)
            }
        }
        composable(
            route = NavCharItem.Details.route,
        ) { navBackStackEntry ->
            val character: Character? =
                navController.previousBackStackEntry?.arguments?.getParcelable("character")
            val viewModel: DetailsViewModel = hiltViewModel(navBackStackEntry)
            character?.let {
                viewModel.setHero(it)
            }
            DetailsScreen(
                viewModel = viewModel,
                onBackPressed = {
                    navController.popBackStack(
                        route = NavCharItem.Characters.route,
                        inclusive = false
                    )
                },
                onComicSelected = {
                    navController.navigate(DeepLinks.Comics.route.toUri())
                },
            )
        }
    }
}
