package com.rumosoft.characters.presentation.screen.state

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.component.HeroDetails
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState.Loading
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState.Success
import com.rumosoft.characters.presentation.viewmodel.state.HeroListProgressIndicator
import com.rumosoft.components.presentation.component.CustomLoading
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun DetailsState.BuildUI(
    onComicSelected: (Int) -> Unit,
) {
    when (this) {
        is Loading -> BuildLoading()
        is Success -> HeroDetails(
            character = character,
            onComicSelected = onComicSelected,
        )
    }
}

@Composable
private fun BuildLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag(HeroListProgressIndicator),
    ) {
        CustomLoading()
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewDetailsSuccess() {
    MarvelComposeTheme {
        Success(SampleData.heroesSample.first()).BuildUI {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsLoading() {
    MarvelComposeTheme {
        Loading
    }
}
