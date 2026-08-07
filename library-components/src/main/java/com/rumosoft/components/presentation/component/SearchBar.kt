package com.rumosoft.components.presentation.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun SearchBar(
    search: String,
    modifier: Modifier = Modifier,
    hint: String? = null,
    requestFocus: Boolean = false,
    onValueChanged: (String) -> Unit = {},
    onLeadingClicked: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val searchTextContentDescription = stringResource(R.string.search_text)
    LaunchedEffect(requestFocus) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }
    TextField(
        value = search,
        onValueChange = { newValue ->
            onValueChanged(newValue)
        },
        placeholder = {
            if (hint != null) {
                Text(
                    text = hint,
                    style = TextStyle(fontSize = 18.sp),
                )
            }
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .semantics { contentDescription = searchTextContentDescription },
        textStyle = TextStyle(color = MarvelComposeTheme.colors.onBackground, fontSize = 18.sp),
        leadingIcon = {
            SearchBarLeadingIcon(onLeadingClicked)
        },
        trailingIcon = {
            if (search != "") {
                SearchBarTrailingIcon(onValueChanged)
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MarvelComposeTheme.colors.onBackground,
            cursorColor = MarvelComposeTheme.colors.onBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() },
        ),
    )
}

@Composable
private fun SearchBarLeadingIcon(onLeadingClicked: () -> Unit) {
    val closeContentDescription = stringResource(id = R.string.search_close)
    IconButton(
        onClick = onLeadingClicked,
        modifier = Modifier.semantics { contentDescription = closeContentDescription },
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_keyboard_arrow_up),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
        )
    }
}

@Composable
private fun SearchBarTrailingIcon(
    onValueChanged: (String) -> Unit,
) {
    val clearContentDescription = stringResource(id = R.string.search_clear)
    IconButton(
        onClick = {
            onValueChanged("")
        },
        modifier = Modifier.semantics { contentDescription = clearContentDescription },
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp),
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SearchViewPreviewEmpty() {
    MarvelComposeTheme {
        SearchBar("")
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SearchViewPreviewWithText() {
    MarvelComposeTheme {
        SearchBar("Spider")
    }
}
