package com.rumosoft.components.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Paddings(
    val defaultPadding: Dp = 16.dp,
    val tinyPadding: Dp = 4.dp,
    val smallPadding: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val largePadding: Dp = 24.dp,
)

internal val LocalPaddings = staticCompositionLocalOf { Paddings() }
