package com.rumosoft.marvelcompose.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.components.presentation.component.SectionTabBar
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme

@Composable
fun ComicsScreen() {
    LazyColumn {
        item {
            SectionTabBar(R.string.comics)
            Text(
                "To be continued...",
                color = MarvelComposeTheme.colors.onBackground,
                modifier = Modifier.padding(MarvelComposeTheme.paddings.defaultPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ComicsScreenPreview() {
    MarvelComposeTheme {
        ComicsScreen()
    }
}
