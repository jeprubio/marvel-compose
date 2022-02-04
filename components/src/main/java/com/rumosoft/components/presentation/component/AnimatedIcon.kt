package com.rumosoft.components.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rumosoft.components.R

private const val ICON_SIZE = 24
val COLOR_NORMAL = Color(0xffEDEFF4)
val COLOR_SELECTED = Color(0xff496DE2)

@Composable
fun AnimatedIcon(
    painter: Painter,
    modifier: Modifier = Modifier,
    iconSize: Dp = ICON_SIZE.dp,
    scale: Float = 1f,
    color: Color = COLOR_NORMAL,
    contentDescription: String? = null,
    animationDuration: Int = 1_000,
    onClick: (() -> Unit)? = null,
) {
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        )
    )

    if (onClick != null) {
        IconButton(
            onClick = onClick,
            modifier = modifier.size(iconSize),
        ) {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                tint = animatedColor,
                modifier = modifier.scale(animatedScale)
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}

@Preview(group = "Icon")
@Composable
fun PreviewIcon() {
    Surface {
        var selected by remember {
            mutableStateOf(false)
        }
        AnimatedIcon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            scale = if (selected) 1.5f else 1f,
            color = if (selected) COLOR_SELECTED else COLOR_NORMAL,
        ) {
            selected = !selected
        }
    }
}
