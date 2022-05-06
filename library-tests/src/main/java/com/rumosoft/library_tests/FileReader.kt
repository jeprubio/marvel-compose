package com.rumosoft.library_tests

object FileReader {
    fun readFile(filePath: String): String? =
        this::class.java.classLoader?.getResourceAsStream(filePath)
            ?.bufferedReader()?.use { it.readText() }
}
