package com.rumosoft.marvelcompose.presentation.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.Text
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.ComicsScreen
import com.rumosoft.components.presentation.deeplinks.DEEP_LINKS_BASE_PATH
import com.rumosoft.components.presentation.deeplinks.Screen
import com.rumosoft.marvelcompose.presentation.MainActivity

@Composable
internal fun WidgetContent() {
    Column(
        modifier = GlanceModifier.fillMaxSize()
            .background(GlanceTheme.colors.background),
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "MARVEL", modifier = GlanceModifier.padding(12.dp))
        Row(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier.fillMaxWidth(),
        ) {
            Button(
                text = "Characters",
                onClick = actionRunCallback<OpenCharactersAction>(),
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            Button(
                text = "Comics",
                onClick = actionRunCallback<OpenComicsAction>(),
            )
        }
    }
}

class OpenCharactersAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        openDeepLink(context, CharactersScreen)
    }
}

class OpenComicsAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        openDeepLink(context, ComicsScreen)
    }
}

private fun openDeepLink(context: Context, screen: Screen) {
    val intent = Intent(context, MainActivity::class.java).apply {
        if (screen is CharactersScreen) {
            data = "$DEEP_LINKS_BASE_PATH/characters".toUri()
        } else {
            data = "$DEEP_LINKS_BASE_PATH/comics".toUri()
        }
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    context.startActivity(intent)
}
