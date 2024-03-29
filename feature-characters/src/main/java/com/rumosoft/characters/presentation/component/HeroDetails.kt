package com.rumosoft.characters.presentation.component

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.rumosoft.characters.R
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.model.ComicSummary
import com.rumosoft.characters.domain.model.Link
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.viewmodel.state.HeroListSuccessResult
import com.rumosoft.components.presentation.component.ComicThumbnail
import com.rumosoft.components.presentation.component.MarvelImage
import com.rumosoft.components.presentation.component.SimpleMessage
import com.rumosoft.components.presentation.theme.CustomDiamond
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import timber.log.Timber

private const val COMIC_SHAPE_DISPLACEMENT = 10f

@Composable
fun HeroDetails(
    character: Character,
    modifier: Modifier = Modifier,
    onComicSelected: (Int) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
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
    modifier: Modifier = Modifier,
) {
    MarvelImage(
        thumbnailUrl = thumbnail,
        contentDescription = contentDescription,
        circular = true,
        noImage = com.rumosoft.components.R.drawable.img_no_image,
        modifier = modifier,
    )
}

@Composable
private fun HeroName(name: String) {
    Text(
        text = name,
        color = MarvelComposeTheme.colors.primary,
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MarvelComposeTheme.paddings.defaultPadding,
                bottom = MarvelComposeTheme.paddings.tinyPadding,
            ),
    )
}

@Composable
fun Links(links: List<Link>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
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
        shape = CustomDiamond(COMIC_SHAPE_DISPLACEMENT),
        modifier = Modifier
            .clickable { context.startActivity(intent) }
            .padding(end = MarvelComposeTheme.paddings.smallPadding),
    ) {
        Text(
            text = link.type.uppercase(),
            modifier = Modifier.padding(
                vertical = 2.dp,
                horizontal = 8.dp,
            ),
        )
    }
}

@Composable
fun Description(description: String) {
    SectionTitle(stringResource(id = R.string.description))
    Text(
        text = description.takeIf { it.isNotEmpty() }
            ?: stringResource(id = R.string.no_description),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun Comics(comics: List<ComicSummary>?, onComicSelected: (Int) -> Unit = {}) {
    SectionTitle(stringResource(id = R.string.comics))
    if (!comics.isNullOrEmpty()) {
        LazyRow {
            items(comics) { comic ->
                comic.thumbnail?.takeIf { it.isNotEmpty() }?.let { thumbnail ->
                    ComicThumbnail(
                        title = comic.title,
                        thumbnail = thumbnail,
                        url = comic.url,
                        onComicSelected = onComicSelected,
                    )
                    Timber.d("thumbnail: $thumbnail")
                }
            }
        }
    } else {
        SimpleMessage(
            message = stringResource(id = R.string.no_comics_info),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun SectionTitle(section: String) {
    Text(
        text = section,
        color = MarvelComposeTheme.colors.primary,
        style = MarvelComposeTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = MarvelComposeTheme.paddings.medium),
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
