package com.rumosoft.characters.presentation.component

import com.rumosoft.characters.domain.model.Character

// Typed key so list and details can't drift apart or collide with other features.
data class CharacterImageKey(val characterId: Long)

fun Character.imageSharedKey(): CharacterImageKey = CharacterImageKey(id)
