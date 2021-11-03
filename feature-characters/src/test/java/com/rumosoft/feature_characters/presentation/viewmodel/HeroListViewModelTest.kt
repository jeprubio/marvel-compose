package com.rumosoft.feature_characters.presentation.viewmodel

import com.rumosoft.feature_characters.MainCoroutineRule
import com.rumosoft.feature_characters.domain.model.Resource
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
import com.rumosoft.feature_characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class HeroListViewModelTest : TestCase() {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    private val searchUseCase: SearchUseCase = mockk()
    private lateinit var heroListViewModel: HeroListViewModel

    private val hero = SampleData.batman

    @Test
    fun `performSearch() calls searchUseCase`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then searchUseCase gets invoked`()
        }
    }

    @Test
    fun `If performSearch() goes well the HeroListScreenState will be Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then HeroListScreenSuccess should be Success`()
        }

    @Test
    fun `If performSearch() returns error the HeroListScreenState will be Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns error`()

            `when initialising the ViewModel`()

            `then HeroListScreenSuccess should be Error`()
        }

    @Test
    fun `If a hero is selected the screen state must change`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns results`()
            `given the ViewModel is initialised`()
            `given the screen state has no selected hero`()

            `when a hero gets selected`()

            `then the screen state selected hero should have been updated`()
        }
    }

    @Test
    fun `If the selected hero is reset the screen state must change`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns results`()
            `given the ViewModel is initialised`()
            `given the screen state has a selected hero`()

            `when the selected hero gets reset`()

            `then the screen state selected hero should have been reset`()
        }
    }

    private fun `given searchUseCase invocation returns results`() {
        coEvery { searchUseCase.invoke("", true) } returns
            Resource.Success(SampleData.heroesSample)
    }

    private fun `given searchUseCase invocation returns error`() {
        coEvery { searchUseCase.invoke("", true) } returns
            Resource.Error(Exception())
    }

    private fun `given the ViewModel is initialised`() {
        heroListViewModel = HeroListViewModel(searchUseCase)
    }

    private fun `given the screen state has no selected hero`() {
        assertNull(heroListViewModel.heroListScreenState.value.selectedHero)
    }

    private fun `given the screen state has a selected hero`() {
        heroListViewModel.heroClicked(hero)
        assertNotNull(heroListViewModel.heroListScreenState.value.selectedHero)
    }

    private fun `when initialising the ViewModel`() {
        heroListViewModel = HeroListViewModel(searchUseCase)
    }

    private fun `when a hero gets selected`() {
        heroListViewModel.heroClicked(hero)
    }

    private fun `when the selected hero gets reset`() {
        heroListViewModel.resetSelectedHero()
    }

    private fun `then searchUseCase gets invoked`() {
        coVerify { searchUseCase.invoke("", true) }
    }

    private fun `then HeroListScreenSuccess should be Success`() {
        assertTrue(heroListViewModel.heroListScreenState.value.heroListState is HeroListState.Success)
    }

    private fun `then HeroListScreenSuccess should be Error`() {
        assertTrue(heroListViewModel.heroListScreenState.value.heroListState is HeroListState.Error)
    }

    private fun `then the screen state selected hero should have been updated`() {
        assertEquals(hero, heroListViewModel.heroListScreenState.value.selectedHero)
    }

    private fun `then the screen state selected hero should have been reset`() {
        assertNull(heroListViewModel.heroListScreenState.value.selectedHero)
    }
}
