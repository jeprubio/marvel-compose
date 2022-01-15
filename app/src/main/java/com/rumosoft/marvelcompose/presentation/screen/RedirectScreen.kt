package com.rumosoft.marvelcompose.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun RedirectScreen(onLoaded: () -> Unit) {
    val currentOnLoaded by rememberUpdatedState(onLoaded)
    LaunchedEffect(key1 = true) {
        currentOnLoaded()
    }
}
