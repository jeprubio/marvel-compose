package com.rumosoft.feature_characters.presentation.navigation

import androidx.navigation.NamedNavArgument
import com.rumosoft.commons.DeepLinks
import com.rumosoft.commons.NavigationCommand

sealed class NavCharItem(
    override val destination: String,
    override val navArgs: List<NamedNavArgument> = emptyList(),
) : NavigationCommand {
    val route = run {
        val argKeys = navArgs.map { "{${it.name}}" }
        listOf(destination)
            .plus(argKeys)
            .joinToString("/")
    }

    object Characters : NavCharItem("characters") {
        val deepLink
            get() = DeepLinks.Characters.route
    }
    object Details : NavCharItem("characterDetails")
}
