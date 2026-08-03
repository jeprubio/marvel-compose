package com.rumosoft.characters.presentation.viewmodel

import com.rumosoft.characters.domain.model.CharactersPage
import com.rumosoft.characters.domain.usecase.GetCharactersUseCase
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
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

    @Test
    fun `Success state propagates hasMorePages when the page reports no more pages`() =
        runTest {
            `given getCharactersUseCase invocation returns results without more pages`()

            `when initialising the ViewModel`()

            `then the Success state reports hasMorePages as false`()
        }

    @Test
    fun `onReachedEnd does not fetch the next page when hasMorePages is false`() =
        runTest {
            `given getCharactersUseCase invocation returns results without more pages`()
            `when initialising the ViewModel`()

            heroListViewModel.onReachedEnd()

            `then the use case is invoked only once`()
        }

    @Test
    fun `onReachedEnd fetches the next page when hasMorePages is true`() =
        runTest {
            `given getCharactersUseCase invocation returns results`()
            `given getCharactersUseCase invocation for page 2 returns results without more pages`()
            `when initialising the ViewModel`()

            heroListViewModel.onReachedEnd()

            `then the use case is invoked for page 2`()
        }

    @Test
    fun `onReachedEnd does not trigger a second fetch while one is already in progress`() =
        runTest {
            val deferred = CompletableDeferred<Result<CharactersPage>>()
            `given getCharactersUseCase invocation returns results`()
            coEvery { getCharactersUseCase.invoke(2) } coAnswers { deferred.await() }
            `when initialising the ViewModel`()

            heroListViewModel.onReachedEnd()
            heroListViewModel.onReachedEnd()

            coVerify(exactly = 1) { getCharactersUseCase.invoke(2) }

            deferred.complete(Result.success(CharactersPage(emptyList(), hasMorePages = false)))
        }

    @Test
    fun `characters from all loaded pages are accumulated in the state`() =
        runTest {
            `given getCharactersUseCase invocation returns results`()
            coEvery { getCharactersUseCase.invoke(2) } returns
                Result.success(CharactersPage(listOf(hero), hasMorePages = false))
            `when initialising the ViewModel`()

            heroListViewModel.onReachedEnd()

            `then the accumulated character list contains items from both pages`()
        }

    private fun `given getCharactersUseCase invocation returns results`() {
        coEvery { getCharactersUseCase.invoke(1) } returns
            Result.success(CharactersPage(SampleData.heroesSample, hasMorePages = true))
    }

    private fun `given getCharactersUseCase invocation returns error`() {
        coEvery { getCharactersUseCase.invoke(1) } returns
            Result.failure(Exception())
    }

    private fun `given getCharactersUseCase invocation returns results without more pages`() {
        coEvery { getCharactersUseCase.invoke(1) } returns
            Result.success(CharactersPage(SampleData.heroesSample, hasMorePages = false))
    }

    private fun `given getCharactersUseCase invocation for page 2 returns results without more pages`() {
        coEvery { getCharactersUseCase.invoke(2) } returns
            Result.success(CharactersPage(emptyList(), hasMorePages = false))
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

    private fun `then the Success state reports hasMorePages as false`() {
        val state = heroListViewModel.heroListScreenState.value.heroListState
        assertTrue(state is HeroListState.Success)
        assertEquals(false, (state as HeroListState.Success).hasMorePages)
    }

    private fun `then the use case is invoked only once`() {
        coVerify(exactly = 1) { getCharactersUseCase.invoke(any()) }
    }

    private fun `then the use case is invoked for page 2`() {
        coVerify { getCharactersUseCase.invoke(2) }
    }

    private fun `then the accumulated character list contains items from both pages`() {
        val state = heroListViewModel.heroListScreenState.value.heroListState as HeroListState.Success
        assertEquals(SampleData.heroesSample.size + 1, state.characters.size)
    }
}
