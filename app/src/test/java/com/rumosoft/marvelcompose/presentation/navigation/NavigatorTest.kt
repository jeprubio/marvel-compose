package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.rumosoft.components.presentation.deeplinks.CharacterDetails
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.ComicDetails
import com.rumosoft.components.presentation.deeplinks.ComicsScreen
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NavigatorTest {

    private lateinit var navigator: Navigator
    private lateinit var state: NavigationState

    @BeforeEach
    fun setUp() {
        val charactersStack = NavBackStack<NavKey>(CharactersScreen)
        val comicsStack = NavBackStack<NavKey>(ComicsScreen)
        state = NavigationState(
            startRoute = CharactersScreen,
            topLevelRoute = mutableStateOf(CharactersScreen),
            backStacks = mapOf(
                CharactersScreen to charactersStack,
                ComicsScreen to comicsStack,
            ),
        )
        navigator = Navigator(state)
    }

    @Test
    fun `navigate to top-level route switches tab`() {
        navigator.navigate(ComicsScreen)

        assertEquals(ComicsScreen, state.topLevelRoute)
    }

    @Test
    fun `navigate to detail route pushes onto current stack`() {
        val detail = CharacterDetails(42L)

        navigator.navigate(detail)

        val stack = state.backStacks[CharactersScreen]!!
        assertEquals(listOf(CharactersScreen, detail), stack.toList())
    }

    @Test
    fun `navigateOnTab switches tab and pushes route`() {
        val comicDetail = ComicDetails(123)

        navigator.navigateOnTab(ComicsScreen, comicDetail)

        assertEquals(ComicsScreen, state.topLevelRoute)
        val stack = state.backStacks[ComicsScreen]!!
        assertEquals(listOf(ComicsScreen, comicDetail), stack.toList())
    }

    @Test
    fun `goBack pops current entry from stack`() {
        navigator.navigate(CharacterDetails(1L))

        navigator.goBack()

        val stack = state.backStacks[CharactersScreen]!!
        assertEquals(listOf<NavKey>(CharactersScreen), stack.toList())
    }

    @Test
    fun `goBack at base of non-start tab returns to start tab`() {
        navigator.navigate(ComicsScreen)
        assertEquals(ComicsScreen, state.topLevelRoute)

        navigator.goBack()

        assertEquals(CharactersScreen, state.topLevelRoute)
    }

    @Test
    fun `switchTabAndNavigate pops current entry and navigates to target tab`() {
        // Simulate being on a character details screen
        navigator.navigate(CharacterDetails(1L))
        assertEquals(
            listOf<NavKey>(CharactersScreen, CharacterDetails(1L)),
            state.backStacks[CharactersScreen]!!.toList(),
        )

        // Switch to comics tab with a comic detail
        val comicDetail = ComicDetails(99)
        navigator.switchTabAndNavigate(ComicsScreen, comicDetail)

        // Current entry popped from characters stack
        assertEquals(
            listOf<NavKey>(CharactersScreen),
            state.backStacks[CharactersScreen]!!.toList(),
        )
        // Switched to comics tab
        assertEquals(ComicsScreen, state.topLevelRoute)
        // Comic detail pushed onto comics stack
        assertEquals(
            listOf<NavKey>(ComicsScreen, comicDetail),
            state.backStacks[ComicsScreen]!!.toList(),
        )
    }

    @Test
    fun `stacksInUse returns only start route when on start tab`() {
        assertEquals(listOf<NavKey>(CharactersScreen), state.stacksInUse)
    }

    @Test
    fun `stacksInUse returns start and current when on different tab`() {
        navigator.navigate(ComicsScreen)

        assertEquals(listOf<NavKey>(CharactersScreen, ComicsScreen), state.stacksInUse)
    }
}

