package com.rumosoft.components.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    state: MutableState<TextFieldValue>,
    onValueChanged: (String) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
            onValueChanged(value.text)
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value.text != "") {
                IconButton(
                    onClick = {
                        onCrossIconPressed(state, onValueChanged)
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MarvelComposeTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
    )
}

private fun onCrossIconPressed(
    state: MutableState<TextFieldValue>,
    onValueChanged: (String) -> Unit
) {
    state.value =
        TextFieldValue("")
    onValueChanged("")
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun SearchViewPreviewEmpty() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    MarvelComposeTheme {
        SearchBar(textState)
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun SearchViewPreviewWithText() {
    val textState = remember { mutableStateOf(TextFieldValue("Spider")) }
    MarvelComposeTheme {
        SearchBar(textState)
    }
}
