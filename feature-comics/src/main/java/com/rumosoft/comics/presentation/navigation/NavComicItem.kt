package com.rumosoft.comics.presentation.navigation

import androidx.navigation.NamedNavArgument
import com.rumosoft.commons.DeepLinks
import com.rumosoft.commons.NavigationCommand

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
    object Details : NavComicItem("comicDetails") {
        val deepLink
            get() = DeepLinks.ComicDetails.route
    }
}
