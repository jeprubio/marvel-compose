package com.rumosoft.components.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

const val RetryButton = "retryButton"

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SimpleMessage(
            message = message,
            modifier = Modifier.fillMaxWidth(),
        )
        if (onRetry != null) {
            Button(
                onClick = { onRetry.invoke() },
                modifier = Modifier.testTag(RetryButton),
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Preview(widthDp = 150, heightDp = 150)
@Composable
fun ErrorMessagePreview() {
    ErrorMessage(message = "Whatever")
}

@Preview(widthDp = 150, heightDp = 150)
@Composable
fun ErrorMessageWithRetryPreview() {
    ErrorMessage(message = "Whatever", onRetry = { })
}
