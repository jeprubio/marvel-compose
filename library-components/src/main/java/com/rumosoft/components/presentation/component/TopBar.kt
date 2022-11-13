package com.rumosoft.components.presentation.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@ExperimentalMaterial3Api
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

@ExperimentalMaterial3Api
@Preview
@Composable
fun TopBarPreview() {
    MarvelComposeTheme {
        TopBar(
            apBarText = "App Bar Text",
            leftIcon = rememberAsyncImagePainter(R.drawable.ic_arrow_back_24)
        )
    }
}
