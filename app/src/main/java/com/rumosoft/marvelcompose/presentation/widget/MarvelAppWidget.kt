package com.rumosoft.marvelcompose.presentation.widget

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.preferencesOf
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.ExperimentalGlanceRemoteViewsApi
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.material3.ColorProviders
import androidx.glance.preview.ExperimentalGlancePreviewApi
import androidx.glance.preview.Preview
import com.google.android.glance.appwidget.host.glance.GlanceAppWidgetHostPreview
import com.rumosoft.components.presentation.theme.DarkColorPalette
import com.rumosoft.components.presentation.theme.LightColorPalette

class MarvelAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            GlanceTheme(
                colors = ColorProviders(
                    light = LightColorPalette,
                    dark = DarkColorPalette,
                ),
            ) {
                WidgetContent()
            }
        }
    }
}

@OptIn(ExperimentalGlancePreviewApi::class, ExperimentalGlanceRemoteViewsApi::class)
@Preview
@Composable
fun PreviewMarvelAppWidget() {
    val displaySize = DpSize(200.dp, 200.dp)
    val state = preferencesOf()

    GlanceAppWidgetHostPreview(
        modifier = Modifier.fillMaxSize(),
        glanceAppWidget = MarvelAppWidget(),
        state = state,
        displaySize = displaySize,
    )
}
