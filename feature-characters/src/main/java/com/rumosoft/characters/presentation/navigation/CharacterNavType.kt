package com.rumosoft.characters.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.marvelapi.infrastructure.extensions.parcelable
import kotlinx.serialization.json.Json

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
