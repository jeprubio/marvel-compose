package com.rumosoft.feature_characters.presentation.viewmodel

import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.feature_characters.MainCoroutineRule
import com.rumosoft.feature_characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
import com.rumosoft.feature_characters.presentation.viewmodel.state.DetailsState
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
internal class DetailsViewModelTest : TestCase() {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    private val getComicThumbnailUseCase: GetComicThumbnailUseCase = mockk()
    private lateinit var detailsViewModel: DetailsViewModel

    private val hero = SampleData.heroesSampleWithoutImages.first()

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
            `given getComicThumbnailUseCase returns url`()

            `when setHero gets called in view model`()

            `then the state is Success`()
        }
    }

    @Test
    fun `If a hero WITH comics is selected getComicThumbnailUseCase should be invoked`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given the view model is initialised`()
            `given the current state is Loading`()
            `given getComicThumbnailUseCase invocation returns results`()

            `when setHero gets called in view model`()

            `then the state is Success`()
            `then getComicThumbnailUseCase should be invoked`()
        }
    }

    @Test
    fun `If a hero WITHOUT comics is selected getComicThumbnailUseCase should NOT be invoked`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given the view model is initialised`()
            `given the current state is Loading`()
            `given getComicThumbnailUseCase invocation returns results`()

            `when setHero with a hero without comics gets called in view model`()

            `then the state is Success`()
            `then getComicThumbnailUseCase should not be invoked`()
        }
    }

    private fun `given the view model is initialised`() {
        detailsViewModel = DetailsViewModel(getComicThumbnailUseCase)
    }

    private fun `given the current state is Loading`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Loading)
    }

    private fun `given getComicThumbnailUseCase invocation returns results`() {
        coEvery { getComicThumbnailUseCase.invoke(any()) } returns
            Resource.Success("thumbnail")
    }

    private fun `given getComicThumbnailUseCase returns url`() {
        coEvery { getComicThumbnailUseCase.invoke(any()) } returns Resource.Success("")
    }

    private fun `when view model is initialised`() {
        detailsViewModel = DetailsViewModel(getComicThumbnailUseCase)
    }

    private fun `when setHero gets called in view model`() {
        detailsViewModel.setHero(hero)
    }

    private fun `when setHero with a hero without comics gets called in view model`() {
        detailsViewModel.setHero(hero.copy(comics = emptyList()))
    }

    private fun `then the state is Loading`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Loading)
    }

    private fun `then the state is Success`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Success)
    }

    private fun `then getComicThumbnailUseCase should be invoked`() {
        coVerify { getComicThumbnailUseCase.invoke(any()) }
    }

    private fun `then getComicThumbnailUseCase should not be invoked`() {
        coVerify(exactly = 0) { getComicThumbnailUseCase.invoke(any()) }
    }
}
