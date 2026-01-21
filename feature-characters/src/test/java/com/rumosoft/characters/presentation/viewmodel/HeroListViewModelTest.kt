package com.rumosoft.characters.presentation.viewmodel

import com.rumosoft.characters.domain.usecase.GetCharactersUseCase
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class HeroListViewModelTest {
    private val getCharactersUseCase: GetCharactersUseCase = mockk()
    private lateinit var heroListViewModel: HeroListViewModel
    private val hero = SampleData.heroesSample.first()

    @Test
    fun `performSearch() calls searchUseCase`() {
        runTest {
            `given getCharactersUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then searchUseCase gets invoked`()
        }
    }

    @Test
    fun `If performSearch() goes well the HeroListScreenState will be Success`() =
        runTest {
            `given getCharactersUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then HeroListScreenSuccess should be Success`()
        }

    @Test
    fun `If performSearch() returns error the HeroListScreenState will be Error`() =
        runTest {
            `given getCharactersUseCase invocation returns error`()

            `when initialising the ViewModel`()

            `then HeroListScreenSuccess should be Error`()
        }

    @Test
    fun `If a hero is selected the screen state must change`() {
        runTest {
            `given getCharactersUseCase invocation returns results`()
            `given the ViewModel is initialised`()
            `given the screen state has no selected hero`()

            `when a hero gets selected`()

            `then the screen state selected hero should have been updated`()
        }
    }

    @Test
    fun `If the selected hero is reset the screen state must change`() {
        runTest {
            `given getCharactersUseCase invocation returns results`()
            `given the ViewModel is initialised`()
            `given the screen state has a selected hero`()

            `when the selected hero gets reset`()

            `then the screen state selected hero should have been reset`()
        }
    }

    private fun `given getCharactersUseCase invocation returns results`() {
        coEvery { getCharactersUseCase.invoke(1) } returns
            Result.success(SampleData.heroesSample)
    }

    private fun `given getCharactersUseCase invocation returns error`() {
        coEvery { getCharactersUseCase.invoke(1) } returns
            Result.failure(Exception())
    }

    private fun `given the ViewModel is initialised`() {
        heroListViewModel = HeroListViewModel(getCharactersUseCase)
    }

    private fun `given the screen state has no selected hero`() {
        assertNull(heroListViewModel.heroListScreenState.value.selectedCharacter)
    }

    private fun `given the screen state has a selected hero`() {
        heroListViewModel.characterClicked(hero)
        assertNotNull(heroListViewModel.heroListScreenState.value.selectedCharacter)
    }

    private fun `when initialising the ViewModel`() {
        heroListViewModel = HeroListViewModel(getCharactersUseCase)
    }

    private fun `when a hero gets selected`() {
        heroListViewModel.characterClicked(hero)
    }

    private fun `when the selected hero gets reset`() {
        heroListViewModel.resetSelectedCharacter()
    }

    private fun `then searchUseCase gets invoked`() {
        coVerify { getCharactersUseCase.invoke(1) }
    }

    private fun `then HeroListScreenSuccess should be Success`() {
        assertTrue(heroListViewModel.heroListScreenState.value.heroListState is HeroListState.Success)
    }

    private fun `then HeroListScreenSuccess should be Error`() {
        assertTrue(heroListViewModel.heroListScreenState.value.heroListState is HeroListState.Error)
    }

    private fun `then the screen state selected hero should have been updated`() {
        assertEquals(hero, heroListViewModel.heroListScreenState.value.selectedCharacter)
    }

    private fun `then the screen state selected hero should have been reset`() {
        assertNull(heroListViewModel.heroListScreenState.value.selectedCharacter)
    }
}
