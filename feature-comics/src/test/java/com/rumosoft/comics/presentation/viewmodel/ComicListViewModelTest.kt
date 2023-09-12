package com.rumosoft.comics.presentation.viewmodel

import com.rumosoft.comics.domain.usecase.GetComicsUseCase
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel.Companion.DEBOUNCE_DELAY
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class ComicListViewModelTest {
    private val comicsUseCase: GetComicsUseCase = mockk()
    private lateinit var comicListViewModel: ComicListViewModel
    private val comic = SampleData.comicsSample.first()

    @Test
    fun `performSearch() calls searchUseCase`() {
        runTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()
            `when delay time has passed`()

            `then searchUseCase gets invoked`()
        }
    }

    @Test
    fun `If performSearch() goes well the HeroListScreenState will be Success`() =
        runTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()
            `when delay time has passed`()

            `then comicListState should be Success`()
        }

    @Test
    fun `If performSearch() returns error the HeroListScreenState will be Error`() =
        runTest {
            `given searchUseCase invocation returns error`()

            `when initialising the ViewModel`()
            `when delay time has passed`()

            `then comicListState should be Error`()
        }

    @Test
    fun `If a comic is selected the screen state must change`() {
        runTest {
            `given searchUseCase invocation returns results`()
            `given the ViewModel is initialised`()
            `given the screen state has no selected comic`()

            `when a comic gets selected`()

            `then the screen state selected comic should have been updated`()
        }
    }

    @Test
    fun `If the selected comic is reset the screen state must change`() {
        runTest {
            `given searchUseCase invocation returns results`()
            `given the ViewModel is initialised`()
            `given the screen state has a selected comic`()

            `when the selected comic gets reset`()

            `then the screen state selected comic should have been reset`()
        }
    }

    private fun `given searchUseCase invocation returns results`() {
        coEvery { comicsUseCase.invoke("", 1) } returns
            Result.success(SampleData.comicsSample)
    }

    private fun `given searchUseCase invocation returns error`() {
        coEvery { comicsUseCase.invoke("", 1) } returns
            Result.failure(Exception())
    }

    private fun `given the ViewModel is initialised`() {
        comicListViewModel = ComicListViewModel(comicsUseCase)
    }

    private fun `given the screen state has no selected comic`() {
        assertNull(comicListViewModel.comicsListScreenState.value.selectedComic)
    }

    private fun `given the screen state has a selected comic`() {
        comicListViewModel.comicClicked(comic)
        assertNotNull(comicListViewModel.comicsListScreenState.value.selectedComic)
    }

    private fun `when initialising the ViewModel`() {
        comicListViewModel = ComicListViewModel(comicsUseCase)
    }

    private suspend fun `when delay time has passed`() {
        delay(DEBOUNCE_DELAY)
    }

    private fun `when a comic gets selected`() {
        comicListViewModel.comicClicked(comic)
    }

    private fun `when the selected comic gets reset`() {
        comicListViewModel.resetSelectedComic()
    }

    private fun `then searchUseCase gets invoked`() {
        coVerify { comicsUseCase.invoke("", 1) }
    }

    private fun `then comicListState should be Success`() {
        assertTrue(comicListViewModel.comicsListScreenState.value.comicListState is ComicListState.Success)
    }

    private fun `then comicListState should be Error`() {
        assertTrue(comicListViewModel.comicsListScreenState.value.comicListState is ComicListState.Error)
    }

    private fun `then the screen state selected comic should have been updated`() {
        assertEquals(comic, comicListViewModel.comicsListScreenState.value.selectedComic)
    }

    private fun `then the screen state selected comic should have been reset`() {
        assertNull(comicListViewModel.comicsListScreenState.value.selectedComic)
    }
}
