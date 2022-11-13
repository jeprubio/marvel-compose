package com.rumosoft.components.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun SimpleMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            color = MarvelComposeTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSimpleMessage() {
    MarvelComposeTheme {
        SimpleMessage(message = "Message")
    }
}
