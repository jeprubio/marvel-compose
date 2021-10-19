package com.rumosoft.marvelcompose.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme

@Composable
fun HeroListScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Hero List Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview() {
    MarvelComposeTheme {
        HeroListScreen()
    }
}
