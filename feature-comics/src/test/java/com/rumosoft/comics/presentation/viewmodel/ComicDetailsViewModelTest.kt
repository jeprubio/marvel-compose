package com.rumosoft.comics.presentation.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.SavedStateHandle
import com.rumosoft.comics.domain.usecase.GetComicDetailsUseCase
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.navigation.NavComicItem
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
internal class ComicDetailsViewModelTest {
    private val comicDetailsUseCase: GetComicDetailsUseCase = mockk()
    private val comicId = 123
    private val savedStateHandle = SavedStateHandle().apply {
        this[NavComicItem.Details.navArgs[0].name] = comicId.toString()
    }
    private lateinit var viewModel: ComicDetailsViewModel

    @Test
    fun `When the view model is created getComicThumbnailUseCase is invoked`() {
        runTest {
            `given use case invocation returns results`()

            `when view model is initialised`()

            `then use case gets invoked`()
        }
    }

    @Test
    fun `When the view model is created and getComicThumbnailUseCase returns result the state is Success`() {
        runTest {
            `given use case invocation returns results`()

            `when view model is initialised`()

            `then detailsState is Success`()
        }
    }

    private fun `given use case invocation returns results`() {
        coEvery { comicDetailsUseCase.invoke(comicId) } returns Result.success(SampleData.comicsSample.first())
    }

    private fun `when view model is initialised`() {
        viewModel = ComicDetailsViewModel(savedStateHandle, comicDetailsUseCase)
    }

    private fun `then use case gets invoked`() {
        coVerify { comicDetailsUseCase.invoke(comicId) }
    }

    private fun `then detailsState is Success`() {
        assertTrue(viewModel.detailsState.value is ComicDetailsState.Success)
    }
}
