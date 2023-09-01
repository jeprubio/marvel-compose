package com.rumosoft.libraryTests

object ScreenshotUtils {
    fun getScreenshotName(): String {
        return "screenshots/${TestUtils.findRunningTestMethodName()}.png"
    }
}
