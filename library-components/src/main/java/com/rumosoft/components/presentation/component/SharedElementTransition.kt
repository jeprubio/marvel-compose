package com.rumosoft.components.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.sharedElementTransition(key: Any): Modifier {
    val sharedTransitionScope = LocalSharedTransitionScope.current ?: return this
    val visibilityScope = LocalSharedElementVisibilityScope.current ?: return this
    return with(sharedTransitionScope) {
        this@sharedElementTransition.sharedElement(
            sharedTransitionScope.rememberSharedContentState(key = key),
            animatedVisibilityScope = visibilityScope,
        )
    }
}
