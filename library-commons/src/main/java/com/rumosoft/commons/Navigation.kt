package com.rumosoft.commons

import androidx.navigation.NamedNavArgument

const val DeepLinkUri = "rumosoft://marvelcompose"

interface NavigationCommand {
    val destination: String
    val navArgs: List<NamedNavArgument>
}

sealed class DeepLinks(val route: String) {
    object Characters : DeepLinks("$DeepLinkUri/characters")
    object Comics : DeepLinks("$DeepLinkUri/comics")
    object ComicDetails : DeepLinks("$DeepLinkUri/comic/{comicId}")
}
