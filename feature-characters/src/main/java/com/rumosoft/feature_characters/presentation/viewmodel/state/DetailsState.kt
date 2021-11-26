package com.rumosoft.feature_characters.presentation.viewmodel.state

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
import com.rumosoft.feature_characters.presentation.component.HeroDetails

sealed class DetailsState {
    @Composable
    abstract fun BuildUI(onComicSelected: () -> Unit)

    object Loading : DetailsState() {
        @Composable
        override fun BuildUI(onComicSelected: () -> Unit) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(HeroListProgressIndicator),
            ) {
                CircularProgressIndicator()
            }
        }
    }

    class Success(val character: Character) : DetailsState() {
        @Composable
        override fun BuildUI(onComicSelected: () -> Unit) {
            HeroDetails(character, onComicSelected)
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewDetailsSuccess() {
    MarvelComposeTheme {
        DetailsState.Success(SampleData.heroesSample.first()).BuildUI {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsLoading() {
    MarvelComposeTheme {
        DetailsState.Loading
    }
}
