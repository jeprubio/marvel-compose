package com.rumosoft.comics.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rumosoft.marvelapi.DeepLinks
import com.rumosoft.marvelapi.NavigationCommand

sealed class NavComicItem(
    final override val destination: String,
    override val navArgs: List<NamedNavArgument> = emptyList(),
) : NavigationCommand {
    val route = run {
        val argKeys = navArgs.map { "{${it.name}}" }
        listOf(destination)
            .plus(argKeys)
            .joinToString("/")
    }

    object Comics : NavComicItem("comics") {
        val deepLink
            get() = DeepLinks.Comics.route
    }

    object Details : NavComicItem(
        destination = "comicDetails",
        navArgs = listOf(navArgument("id") { type = NavType.IntType }),
    ) {
        val deepLink
            get() = DeepLinks.ComicDetails.route

        fun routeOfComic(comicId: Int): String =
            "$destination/$comicId"
    }
}
