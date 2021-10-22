package com.rumosoft.marvelcompose.presentation.viewmodel

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.viewmodel.state.DetailsState
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class DetailsViewModelTest : TestCase() {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    private lateinit var detailsViewModel: DetailsViewModel

    private val hero = SampleData.batman

    @Test
    fun `When the view model is created the state is Loading`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `when view model is initialised`()

            `then the state is Loading`()
        }
    }

    @Test
    fun `If a hero is selected the screen state must change`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given the view model is initialised`()
            `given the current state is Loading`()

            `when setHero gets called in view model`()

            `then the state is Success`()
        }
    }

    private fun `given the view model is initialised`() {
        detailsViewModel = DetailsViewModel()
    }

    private fun `given the current state is Loading`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Loading)
    }

    private fun `when view model is initialised`() {
        detailsViewModel = DetailsViewModel()
    }

    private fun `when setHero gets called in view model`() {
        detailsViewModel.setHero(hero)
    }

    private fun `then the state is Loading`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Loading)
    }

    private fun `then the state is Success`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Success)
    }
}
