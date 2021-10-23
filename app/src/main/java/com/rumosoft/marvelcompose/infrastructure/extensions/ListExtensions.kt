package com.rumosoft.marvelcompose.infrastructure.extensions

fun <T> List<T>.update(index: Int, item: T): List<T> =
    toMutableList().apply { this[index] = item }
