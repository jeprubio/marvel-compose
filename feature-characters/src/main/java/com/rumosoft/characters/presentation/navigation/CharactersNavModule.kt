package com.rumosoft.characters.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.rumosoft.commons.DeepLinks
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.characters.presentation.screen.DetailsScreen
import com.rumosoft.characters.presentation.screen.HeroListScreen
import com.rumosoft.characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.characters.presentation.viewmodel.HeroListViewModel
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
                onCharacterSelected = { selectedCharacter ->
                    viewModel.resetSelectedCharacter()
                    navController.currentBackStackEntry?.arguments?.putParcelable(
                        "character",
                        selectedCharacter
                    )
                    navController.navigate(NavCharItem.Details.route)
                }
            )
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
                onComicSelected = { comicId ->
                    navController.currentBackStackEntry?.arguments?.putInt(
                        "comicId",
                        comicId
                    )
                    navController.navigate(DeepLinks.ComicDetails.route.toUri())
                },
            )
        }
    }
}