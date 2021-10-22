package com.rumosoft.marvelcompose.presentation.viewmodel.state

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.component.HeroImage
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme

sealed class DetailsState {
    @Composable
    abstract fun BuildUI()

    object Loading : DetailsState() {
        @Composable
        override fun BuildUI() {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(ProgressIndicator),
            ) {
                CircularProgressIndicator()
            }
        }
    }

    class Success(private val hero: Hero) : DetailsState() {
        @Composable
        override fun BuildUI() {
            val scrollState = rememberScrollState()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .testTag(SuccessResult),
            ) {
                HeroImage(
                    thumbnailUrl = hero.thumbnail,
                    modifier = Modifier
                        .height(400.dp)
                )
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsSuccess() {
    MarvelComposeTheme {
        DetailsState.Success(SampleData.batman).BuildUI()
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDetailsSuccessDarkMode() {
    MarvelComposeTheme {
        DetailsState.Success(SampleData.batman).BuildUI()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsLoading() {
    MarvelComposeTheme {
        DetailsState.Loading
    }
}
