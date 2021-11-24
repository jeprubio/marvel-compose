package com.rumosoft.components.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.transform.CircleCropTransformation
import com.rumosoft.components.R

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
        Image(
            painter = rememberImagePainter(
                data = thumbnailUrl,
                builder = {
                    if (circular) {
                        transformations(
                            CircleCropTransformation()
                        )
                    }
                    crossfade(true)
                    placeholder(R.mipmap.ic_launcher)
                    if (originalSize) size(OriginalSize)
                }
            ),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else if (noImage != null) {
        Image(
            painterResource(id = noImage),
            contentDescription = stringResource(id = R.string.no_image),
            modifier = modifier
        )
    }
}
