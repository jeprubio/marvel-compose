package com.rumosoft.components.presentation.theme

import androidx.compose.runtime.compositionLocalOf
import com.airbnb.lottie.compose.LottieConstants

@JvmInline
value class LottieAnimationIterations(val iterations: Int)

val LocalLottieAnimationIterations = compositionLocalOf { LottieAnimationIterations(LottieConstants.IterateForever) }
