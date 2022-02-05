package com.rumosoft.components.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.LocalLottieAnimationIterations

@Composable
fun CustomLoading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ironman_loader))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LocalLottieAnimationIterations.current.iterations
    )

    LottieAnimation(
        composition,
        progress,
        modifier = Modifier.size(100.dp)
    )
}
