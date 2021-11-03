package com.rumosoft.feature_characters.domain.model

sealed class Resource<out T> {
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}
