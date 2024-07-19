package com.rumosoft.marvelcompose.presentation.widget

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.testing.unit.runGlanceAppWidgetUnitTest
import androidx.glance.testing.unit.assertHasClickAction
import androidx.glance.testing.unit.hasText
import org.junit.Test

class WidgetContentKtTest {
    @Test
    fun widgetContent_checkTextContent() = runGlanceAppWidgetUnitTest {
        setAppWidgetSize(DpSize(200.dp, 100.dp))

        provideComposable {
            WidgetContent()
        }

        onNode(hasText("MARVEL"))
            .assertExists()
        onNode(hasText("Characters"))
            .assertExists()
        onNode(hasText("Comics"))
            .assertExists()
    }

    @Test
    fun widgetContent_checkClickableButtons() = runGlanceAppWidgetUnitTest {
        setAppWidgetSize(DpSize(200.dp, 100.dp))

        provideComposable {
            WidgetContent()
        }

        onNode(hasText("Characters"))
            .assertHasClickAction()
        onNode(hasText("Comics"))
            .assertHasClickAction()
    }
}