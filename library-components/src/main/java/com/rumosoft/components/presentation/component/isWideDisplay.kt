package com.rumosoft.components.presentation.component

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun isExpandedDisplay(activity: Activity? = LocalActivity.current): Boolean {
    if (activity == null) return false

    val windowSizeClass = calculateWindowSizeClass(activity = activity)
    val isExpandedDisplay: Boolean by remember {
        derivedStateOf {
            windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Expanded
        }
    }
    return isExpandedDisplay
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun isWindowCompact(activity: Activity? = LocalActivity.current): Boolean {
    if (activity == null) return true

    val windowSizeClass = calculateWindowSizeClass(activity = activity)
    val isWindowCompact: Boolean by remember {
        derivedStateOf {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
        }
    }
    return isWindowCompact
}
