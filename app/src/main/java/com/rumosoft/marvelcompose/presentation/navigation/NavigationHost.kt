package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.characters.presentation.navigation.charactersGraph
import com.rumosoft.comics.presentation.navigation.comicsGraph

const val NAVIGATION_HOST_TEST_TAG = "NavigationHost"

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        startDestination = NavCharItem.Characters.route,
        modifier = modifier.testTag(NAVIGATION_HOST_TEST_TAG)
    ) {
        charactersGraph(navController)
        comicsGraph(navController)
    }
}
