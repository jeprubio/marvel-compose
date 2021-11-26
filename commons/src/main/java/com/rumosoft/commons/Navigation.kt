package com.rumosoft.commons

import androidx.navigation.NamedNavArgument

const val DeepLinkUri = "rumosoft://marvelcompose"

interface NavigationCommand {
    val destination: String
    val navArgs: List<NamedNavArgument>
}
