package com.rumosoft.components.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

val DarkColorPalette = darkColorScheme(
    primary = Ribbon500,
    secondary = Ribbon700,
    tertiary = Skin400,
    background = Color.Black,
    surface = Ribbon500,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceContainer = Ribbon500,
    onPrimaryContainer = Color.LightGray,
)

val LightColorPalette = lightColorScheme(
    primary = Ribbon500,
    secondary = Ribbon700,
    tertiary = Skin200,
    background = Color.White,
    surface = Ribbon500,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = MineShaft,
    onBackground = MineShaft,
    onSurface = Color.White,
    onError = Color.White,
    surfaceContainer = Ribbon500,
    onPrimaryContainer = Color.LightGray,
)

@Composable
fun MarvelComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalPaddings provides Paddings(),
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

object MarvelComposeTheme {
    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val paddings: Paddings
        @Composable
        get() = LocalPaddings.current
}
