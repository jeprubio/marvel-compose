package com.rumosoft.components.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.rumosoft.components.R

const val MarvelImage = "marvelImage"
const val Placeholder = "placeholder"

@Composable
fun MarvelImage(
    thumbnailUrl: String,
    modifier: Modifier = Modifier,
    circular: Boolean = false,
    originalSize: Boolean = false,
    contentDescription: String? = null,
    @DrawableRes noImage: Int? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    if (thumbnailUrl.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUrl)
                .crossfade(enable = true)
                .placeholder(R.mipmap.ic_launcher)
                .apply {
                    if (circular) {
                        transformations(
                            CircleCropTransformation()
                        )
                    }
                    if (originalSize) {
                        size(Size.ORIGINAL)
                    }
                }
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier.testTag(MarvelImage)
        )
    } else if (noImage != null) {
        Image(
            painterResource(id = noImage),
            contentDescription = stringResource(id = R.string.no_image),
            modifier = modifier.testTag(Placeholder)
        )
    }
}

@Preview(widthDp = 150, heightDp = 150)
@Composable
fun MarvelImageNoImagePreview() {
    MarvelImage(thumbnailUrl = "", noImage = R.drawable.img_no_image)
}
