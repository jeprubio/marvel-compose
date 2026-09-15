package com.rumosoft.comics.presentation.component

import com.rumosoft.components.presentation.component.LocalSharedElementVisibilityScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsSuccessResult
import com.rumosoft.components.presentation.component.LocalSharedTransitionScope
import com.rumosoft.components.presentation.component.MarvelImage
import com.rumosoft.components.presentation.component.isExpandedDisplay
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicDetails(
    comic: Comic,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalSharedElementVisibilityScope.current
    val imageContentScale = if (isExpandedDisplay())
        ContentScale.Inside
    else
        ContentScale.FillWidth

    LazyColumn(modifier = modifier) {
        item {
            comic.thumbnail?.let {
                val sharedModifier = if (sharedTransitionScope != null && animatedContentScope != null) {
                    with(sharedTransitionScope) {
                        Modifier.sharedElement(
                            rememberSharedContentState(key = "comic_image_${comic.id}"),
                            animatedVisibilityScope = animatedContentScope,
                        )
                    }
                } else {
                    Modifier
                }
                MarvelImage(
                    thumbnailUrl = it,
                    modifier = sharedModifier
                        .height(570.dp)
                        .fillMaxWidth(),
                    contentDescription = comic.title,
                    contentScale = imageContentScale,
                )
            }
            Text(
                text = comic.title,
                modifier = Modifier
                    .testTag(ComicDetailsSuccessResult)
                    .padding(MarvelComposeTheme.paddings.defaultPadding),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewComicDetails() {
    MarvelComposeTheme {
        ComicDetails(
            comic = SampleData.comicsSample.first()
        )
    }
}
