package com.rumosoft.characters.presentation.component

import com.rumosoft.characters.domain.model.Character
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CharacterSharedElementKeyTest {
    @Test
    fun `imageSharedKey uses character id`() {
        val character = Character(
            id = 101L,
            name = "Hero",
            description = "",
            thumbnail = "thumbnail",
        )

        assertEquals(CharacterImageKey(characterId = 101L), character.imageSharedKey())
    }

    @Test
    fun `imageSharedKey differs per character id`() {
        val first = Character(1L, "A", "", "thumbnail")
        val second = Character(2L, "B", "", "thumbnail")

        assertEquals(CharacterImageKey(1L), first.imageSharedKey())
        assertEquals(CharacterImageKey(2L), second.imageSharedKey())
    }
}
