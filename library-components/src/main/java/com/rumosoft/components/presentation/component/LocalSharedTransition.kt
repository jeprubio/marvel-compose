package com.rumosoft.components.presentation.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf

val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

val LocalSharedElementVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
