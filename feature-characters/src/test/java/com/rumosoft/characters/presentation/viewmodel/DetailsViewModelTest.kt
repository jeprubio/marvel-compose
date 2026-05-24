package com.rumosoft.characters.presentation.viewmodel

import com.rumosoft.characters.domain.usecase.GetCharacterDetailsUseCase
import com.rumosoft.characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.characters.infrastructure.sampleData.SampleData
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
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase = mockk()
    private lateinit var detailsViewModel: DetailsViewModel
    private val hero = SampleData.heroesSampleWithoutImages.first()

    @Test
    fun `When the view model is created getComicThumbnailUseCase is invoked`() {
        runTest {
            `given getComicThumbnailUseCase invocation returns results`()
            `given getCharacterDetailsUseCase invocation returns results`()

            `when view model is initialised`()

            `then getComicThumbnailUseCase should be invoked`()
        }
    }

    @Test
    fun `When the view model is created and getComicThumbnailUseCase returns result the state is Success`() {
        runTest {
            `given getComicThumbnailUseCase invocation returns results`()
            `given getCharacterDetailsUseCase invocation returns results`()

            `when view model is initialised`()

            `then the state is Success`()
        }
    }

    @Test
    fun `When getCharacterDetailsUseCase returns failure the state is Error`() {
        runTest {
            `given getCharacterDetailsUseCase invocation returns failure`()

            `when view model is initialised`()

            `then the state is Error`()
        }
    }

    @Test
    fun `When getCharacterDetailsUseCase returns null the state is Error`() {
        runTest {
            coEvery { getCharacterDetailsUseCase.invoke(hero.id) } returns Result.success(null)

            `when view model is initialised`()

            `then the state is Error`()
        }
    }

    @Test
    fun `When retry is called after error and succeeds the state is Success`() {
        runTest {
            `given getCharacterDetailsUseCase invocation returns failure`()
            `when view model is initialised`()
            `then the state is Error`()

            `given getComicThumbnailUseCase invocation returns results`()
            `given getCharacterDetailsUseCase invocation returns results`()
            detailsViewModel.retry()

            `then the state is Success`()
        }
    }

    @Test
    fun `When retry is called after error getCharacterDetailsUseCase is invoked again`() {
        runTest {
            `given getCharacterDetailsUseCase invocation returns failure`()
            `when view model is initialised`()

            `given getComicThumbnailUseCase invocation returns results`()
            `given getCharacterDetailsUseCase invocation returns results`()
            detailsViewModel.retry()

            coVerify(exactly = 2) { getCharacterDetailsUseCase.invoke(hero.id) }
        }
    }

    private fun `given getComicThumbnailUseCase invocation returns results`() {
        coEvery { getComicThumbnailUseCase.invoke(any()) } returns
            Result.success("thumbnail")
    }

    private fun `given getCharacterDetailsUseCase invocation returns results`() {
        coEvery { getCharacterDetailsUseCase.invoke(hero.id) } returns Result.success(hero)
    }

    private fun `given getCharacterDetailsUseCase invocation returns failure`() {
        coEvery { getCharacterDetailsUseCase.invoke(hero.id) } returns
            Result.failure(Exception("Network error"))
    }

    private fun `when view model is initialised`() {
        detailsViewModel = DetailsViewModel(
            getComicThumbnailUseCase,
            getCharacterDetailsUseCase,
        )
        detailsViewModel.initialize(hero.id)
    }

    private fun `then the state is Success`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Success)
    }

    private fun `then the state is Error`() {
        assertTrue(detailsViewModel.detailsState.value is DetailsState.Error)
    }

    private fun `then getComicThumbnailUseCase should be invoked`() {
        coVerify { getComicThumbnailUseCase.invoke(any()) }
    }
}
