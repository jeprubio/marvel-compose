package com.rumosoft.components.presentation.deeplinks

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DeepLinksKtTest {

    @Test
    fun `resolveDeepLinkRoute maps comics path to ComicsScreen`() {
        val route = resolveDeepLinkRoute("$DEEP_LINKS_BASE_PATH/comics")

        assertEquals(ComicsScreen, route)
    }

    @Test
    fun `resolveDeepLinkRoute maps characters path to CharactersScreen`() {
        val route = resolveDeepLinkRoute("$DEEP_LINKS_BASE_PATH/characters")

        assertEquals(CharactersScreen, route)
    }

    @Test
    fun `resolveDeepLinkRoute maps unknown path to null`() {
        val route = resolveDeepLinkRoute("$DEEP_LINKS_BASE_PATH/unknown")

        assertNull(route)
    }

    @Test
    fun `resolveDeepLinkRoute maps unrelated uri to null`() {
        val route = resolveDeepLinkRoute("https://example.com/comics")

        assertNull(route)
    }

    @Test
    fun `resolveDeepLinkRoute maps root path with no suffix to null`() {
        val route = resolveDeepLinkRoute(DEEP_LINKS_BASE_PATH)

        assertNull(route)
    }
}

