package com.rumosoft.marvelcompose.presentation.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
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
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.comics.presentation.navigation.NavComicItem

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
        openDeepLink(context, NavCharItem.Characters.deepLink)
    }
}

class OpenComicsAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        openDeepLink(context, NavComicItem.Comics.deepLink)
    }
}

private fun openDeepLink(context: Context, deepLink: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink)).apply {
        setPackage(context.packageName)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    context.startActivity(intent)
}