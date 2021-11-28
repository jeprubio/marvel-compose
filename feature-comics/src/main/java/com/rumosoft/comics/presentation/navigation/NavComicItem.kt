package com.rumosoft.comics.presentation.navigation

import androidx.navigation.NamedNavArgument
import com.rumosoft.commons.DeepLinks
import com.rumosoft.commons.NavigationCommand

sealed class NavComicItem(
    final override val destination: String,
    override val navArgs: List<NamedNavArgument> = emptyList(),
) : NavigationCommand {
    val route = destination

    object Comics : NavComicItem("comics") {
        val deepLink
            get() = DeepLinks.Comics.route
    }
}
