package com.rumosoft.components.presentation.component

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun SectionTopBar(
    @StringRes sectionName: Int,
    icon: ImageVector? = null,
    onIconClick: () -> Unit = {}
) {
    val marvelFont = Font(R.font.marvel_regular)

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = sectionName).uppercase(),
                color = Color.White,
                fontSize = 70.sp,
                fontFamily = FontFamily(marvelFont),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(MarvelComposeTheme.paddings.defaultPadding),
            )
        },
        actions = {
            if (icon != null) {
                IconButton(onClick = onIconClick) {
                    Icon(
                        imageVector = icon,
                        tint = Color.White,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier
                            .padding(MarvelComposeTheme.paddings.medium),
                    )
                }
            }
        },
        modifier = Modifier.height(100.dp),
    )
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun SectionTabBarPreview() {
    MarvelComposeTheme {
        SectionTopBar(R.string.app_name)
    }
}
