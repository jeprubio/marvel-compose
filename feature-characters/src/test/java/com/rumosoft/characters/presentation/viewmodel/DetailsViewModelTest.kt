package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.rumosoft.characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class DetailsViewModelTest {
    private val getComicThumbnailUseCase: GetComicThumbnailUseCase = mockk()
    private lateinit var detailsViewModel: DetailsViewModel
    private val hero = SampleData.heroesSampleWithoutImages.first()
    private val savedStateHandle = SavedStateHandle().apply {
        this[NavCharItem.Details.navArgs[0].name] = hero
    }

    @Test
    fun `When the view model is created getComicThumbnailUseCase is invoked`() {
        runTest {
            `given getComicThumbnailUseCase invocation returns results`()

            `when view model is initialised`()

            `then getComicThumbnailUseCase should be invoked`()
        }
    }

    @Test
    fun `When the view model is created and getComicThumbnailUseCase returns result the state is Success`() {
        runTest {
            `given getComicThumbnailUseCase invocation returns results`()

            `when view model is initialised`()

            `then the state is Success`()
        }
    }

    private fun `given getComicThumbnailUseCase invocation returns results`() {
        coEvery { getComicThumbnailUseCase.invoke(any()) } returns
            Result.success("thumbnail")
    }

    private fun `when view model is initialised`() {
        detailsViewModel = DetailsViewModel(savedStateHandle, getComicThumbnailUseCase)
    }

    private fun `then the state is Success`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Success)
    }

    private fun `then getComicThumbnailUseCase should be invoked`() {
        coVerify { getComicThumbnailUseCase.invoke(any()) }
    }
}
