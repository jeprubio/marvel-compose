package com.rumosoft.marvelcompose.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.presentation.navigation.Screen
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import kotlinx.coroutines.delay

const val THREE_SECS = 3_000L

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(THREE_SECS)
        navController.popBackStack()
        navController.navigate(Screen.HeroList.route)
    }

    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ironman_loader))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(150.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    MarvelComposeTheme {
        SplashScreenContent()
    }
}
