package com.rumosoft.commons.domain.model

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.navigation.NavType
import com.rumosoft.commons.infrastructure.extensions.parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Keep
@Parcelize
@Serializable
data class Character(
    val name: String,
    val description: String,
    val thumbnail: String,
    val links: List<Link>? = null,
    val comics: List<ComicSummary>? = null,
) : Parcelable

@Keep
@Parcelize
@Serializable
data class Link(
    val type: String,
    val url: String,
) : Parcelable

val CharacterNavType = object : NavType<Character>(isNullableAllowed = false) {
    override fun put(bundle: Bundle, key: String, value: Character) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): Character? {
        return bundle.parcelable(key) as Character?
    }

    override fun parseValue(value: String): Character {
        return Json.decodeFromString(Uri.decode(value))
    }
}
