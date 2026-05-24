package com.rumosoft.comics.presentation.viewmodel

import com.rumosoft.comics.domain.usecase.GetComicDetailsUseCase
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class ComicDetailsViewModelTest {
    private val comicDetailsUseCase: GetComicDetailsUseCase = mockk()
    private val comicId = 123
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

    @Test
    fun `When use case returns failure the state is Error`() {
        runTest {
            `given use case invocation returns failure`()

            `when view model is initialised`()

            `then detailsState is Error`()
        }
    }

    @Test
    fun `When retry is called after error and succeeds the state is Success`() {
        runTest {
            `given use case invocation returns failure`()
            `when view model is initialised`()
            `then detailsState is Error`()

            `given use case invocation returns results`()
            viewModel.retry()

            `then detailsState is Success`()
        }
    }

    @Test
    fun `When retry is called after error the use case is invoked again`() {
        runTest {
            `given use case invocation returns failure`()
            `when view model is initialised`()

            `given use case invocation returns results`()
            viewModel.retry()

            coVerify(exactly = 2) { comicDetailsUseCase.invoke(comicId) }
        }
    }

    private fun `given use case invocation returns results`() {
        coEvery { comicDetailsUseCase.invoke(comicId) } returns Result.success(SampleData.comicsSample.first())
    }

    private fun `given use case invocation returns failure`() {
        coEvery { comicDetailsUseCase.invoke(comicId) } returns
            Result.failure(Exception("Network error"))
    }

    private fun `when view model is initialised`() {
        viewModel = ComicDetailsViewModel(comicDetailsUseCase)
        viewModel.initialize(comicId)
    }

    private fun `then use case gets invoked`() {
        coVerify { comicDetailsUseCase.invoke(comicId) }
    }

    private fun `then detailsState is Success`() {
        assertTrue(viewModel.detailsState.value is ComicDetailsState.Success)
    }

    private fun `then detailsState is Error`() {
        assertTrue(viewModel.detailsState.value is ComicDetailsState.Error)
    }
}
