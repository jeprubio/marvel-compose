package com.rumosoft.marvelcompose.presentation.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.material3.ColorProviders
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
