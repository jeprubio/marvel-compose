package com.rumosoft.components.presentation.deeplinks

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface Screen

@Serializable
data object CharactersScreen : Screen, NavKey

@Serializable
data class CharacterDetails(val characterId: Long) : Screen, NavKey

@Serializable
data object ComicsScreen : Screen, NavKey

@Serializable
data class ComicDetails(val comicId: Int) : Screen, NavKey

const val DEEP_LINKS_BASE_PATH = "rumosoft://marvelcompose"

/**
 * Maps a deep-link URI (e.g. "rumosoft://marvelcompose/comics") to the [NavKey] it should
 * navigate to, or `null` if the URI doesn't match any known deep link path.
 *
 * Pure function with no Android dependencies (no [android.content.Intent]/[android.net.Uri]),
 * so callers can resolve the destination from any URI representation and the routing rules can
 * be unit tested in isolation.
 */
fun resolveDeepLinkRoute(uriString: String): NavKey? {
    val path = uriString.removePrefix(DEEP_LINKS_BASE_PATH)
    return when {
        path.startsWith("/comics") -> ComicsScreen
        path.startsWith("/characters") -> CharactersScreen
        else -> null
    }
}

