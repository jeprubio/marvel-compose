package com.rumosoft.components.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColors(
    val colorSelectedTab: Color = Color.White,
    val colorUnselectedTab: Color = Ribbon100,
)

internal val LocalColors = staticCompositionLocalOf { CustomColors() }
