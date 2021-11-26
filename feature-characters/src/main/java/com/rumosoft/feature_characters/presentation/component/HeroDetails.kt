package com.rumosoft.feature_characters.presentation.component

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.domain.model.Link
import com.rumosoft.components.presentation.component.ComicThumbnail
import com.rumosoft.components.presentation.component.MarvelImage
import com.rumosoft.components.presentation.component.SimpleMessage
import com.rumosoft.components.presentation.theme.CustomDiamond
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.feature_characters.R
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
import com.rumosoft.feature_characters.presentation.viewmodel.state.HeroListSuccessResult
import timber.log.Timber

@Composable
fun HeroDetails(character: Character, onComicSelected: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .testTag(HeroListSuccessResult)
            .padding(horizontal = MarvelComposeTheme.paddings.defaultPadding)
            .padding(bottom = MarvelComposeTheme.paddings.defaultPadding),
    ) {
        val avatarModifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
            .padding(top = MarvelComposeTheme.paddings.defaultPadding)
            .size(150.dp)
            .clip(CircleShape)
        HeroImage(character.thumbnail, character.name, avatarModifier)
        HeroName(character.name)
        character.links?.let { links ->
            Links(links, Modifier.align(alignment = Alignment.CenterHorizontally))
        }
        Description(character.description)
        Comics(character.comics, onComicSelected)
    }
}

@Composable
fun HeroImage(
    thumbnail: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    MarvelImage(
        thumbnailUrl = thumbnail,
        contentDescription = contentDescription,
        circular = true,
        noImage = R.drawable.img_no_image,
        modifier = modifier,
    )
}

@Composable
private fun HeroName(name: String) {
    Text(
        text = name,
        color = MarvelComposeTheme.colors.primary,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MarvelComposeTheme.paddings.defaultPadding,
                bottom = MarvelComposeTheme.paddings.tinyPadding,
            )
    )
}

@Composable
fun Links(links: List<Link>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
    ) {
        items(links) { url ->
            Urls(url)
        }
    }
}

@Composable
fun Urls(link: Link) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link.url)) }
    Surface(
        color = MarvelComposeTheme.colors.surface,
        border = BorderStroke(1.dp, MarvelComposeTheme.colors.onSecondary),
        shape = CustomDiamond(10f),
        elevation = 8.dp,
        modifier = Modifier
            .clickable { context.startActivity(intent) }
            .padding(end = MarvelComposeTheme.paddings.smallPadding),
    ) {
        Text(
            text = link.type.uppercase(),
            modifier = Modifier.padding(
                vertical = 2.dp,
                horizontal = 8.dp,
            )
        )
    }
}

@Composable
fun Description(description: String) {
    SectionTitle(stringResource(id = R.string.description))
    Text(
        text = description.takeIf { it.isNotEmpty() }
            ?: stringResource(id = R.string.no_description),
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun Comics(comics: List<Comic>?, onComicSelected: () -> Unit = {}) {
    SectionTitle(stringResource(id = R.string.comics))
    if (!comics.isNullOrEmpty()) {
        LazyRow {
            items(comics) { comic ->
                comic.thumbnail?.takeIf { it.isNotEmpty() }?.let { thumbnail ->
                    ComicThumbnail(
                        title = comic.name,
                        thumbnail = thumbnail,
                        onComicSelected = onComicSelected
                    )
                    Timber.d("thumbnail: $thumbnail")
                }
            }
        }
    } else {
        SimpleMessage(
            message = stringResource(id = R.string.no_comics_info),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SectionTitle(section: String) {
    Text(
        text = section,
        color = MarvelComposeTheme.colors.primary,
        style = MarvelComposeTheme.typography.subtitle1,
        modifier = Modifier.padding(vertical = MarvelComposeTheme.paddings.medium)
    )
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewHeroDetails() {
    MarvelComposeTheme {
        HeroDetails(SampleData.heroesSample.first())
    }
}
