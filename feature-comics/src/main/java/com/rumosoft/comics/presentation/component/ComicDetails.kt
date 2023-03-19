package com.rumosoft.comics.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsSuccessResult
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.components.presentation.component.MarvelImage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicDetails(
    comic: Comic,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            comic.thumbnail?.let {
                MarvelImage(
                    thumbnailUrl = it,
                    modifier = Modifier
                        .height(570.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
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
