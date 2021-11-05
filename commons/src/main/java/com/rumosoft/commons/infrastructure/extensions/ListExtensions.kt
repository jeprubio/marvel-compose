package com.rumosoft.commons.infrastructure.extensions

fun <T> List<T>.update(index: Int, item: T): List<T> =
    toMutableList().apply { this[index] = item }
