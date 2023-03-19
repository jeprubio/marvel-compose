package com.rumosoft.characters.presentation.navigation

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.rumosoft.commons.DeepLinks
import com.rumosoft.commons.NavigationCommand
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.domain.model.CharacterNavType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

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
    object Details : NavCharItem(
        destination = "characterDetails",
        navArgs = listOf(navArgument("character") { type = CharacterNavType }),
    ) {
        fun routeOfCharacter(character: Character): String =
            "$destination/${Uri.encode(Json.encodeToJsonElement(character).toString())}"
    }
}
