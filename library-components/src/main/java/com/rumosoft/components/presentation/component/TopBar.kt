package com.rumosoft.components.presentation.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun TopBar(
    apBarText: String,
    leftIcon: Painter,
    leftIconPressed: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = apBarText) },
        navigationIcon = {
            IconButton(onClick = { leftIconPressed.invoke() }) {
                Icon(
                    leftIcon,
                    contentDescription = "Menu Button",
                    tint = Color.White,
                )
            }
        },
    )
}

@Preview
@Composable
fun TopBarPreview() {
    MarvelComposeTheme {
        TopBar(
            apBarText = "App Bar Text",
            leftIcon = rememberImagePainter(R.drawable.ic_arrow_back_24)
        )
    }
}
