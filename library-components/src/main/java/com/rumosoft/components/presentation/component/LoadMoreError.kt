package com.rumosoft.components.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

const val LoadMoreErrorTag = "loadMoreError"
const val LoadMoreRetryTag = "loadMoreRetry"

/**
 * Compact inline error shown at the end of a paginated list when loading more
 * items fails. Unlike [ErrorMessage], it does not take over the whole screen,
 * so the items loaded so far stay visible.
 */
@Composable
fun LoadMoreError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = MarvelComposeTheme.paddings.smallPadding,
                horizontal = MarvelComposeTheme.paddings.defaultPadding,
            )
            .testTag(LoadMoreErrorTag),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.error_data_message),
            textAlign = TextAlign.Center,
            style = MarvelComposeTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier.testTag(LoadMoreRetryTag),
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadMoreErrorPreview() {
    MarvelComposeTheme {
        LoadMoreError(onRetry = {})
    }
}
