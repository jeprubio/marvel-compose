package com.rumosoft.components.presentation.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

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
                    contentDescription = "Menu Btn"
                )
            }
        },
    )
}
