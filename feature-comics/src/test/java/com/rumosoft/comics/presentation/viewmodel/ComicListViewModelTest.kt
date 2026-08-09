package com.rumosoft.comics.presentation.viewmodel

import com.rumosoft.comics.domain.model.ComicsPage
import com.rumosoft.comics.domain.model.RequestInProgressException
import com.rumosoft.comics.domain.usecase.GetComicsUseCase
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
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
internal class ComicListViewModelTest {
    private val comicsUseCase: GetComicsUseCase = mockk()
    private lateinit var comicListViewModel: ComicListViewModel
    private val comic = SampleData.comicsSample.first()

    @Test
    fun `performSearch() calls searchUseCase`() {
        runTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then searchUseCase gets invoked`()
        }
    }

    @Test
    fun `If performSearch() goes well the HeroListScreenState will be Success`() =
        runTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then comicListState should be Success`()
        }

    @Test
    fun `If performSearch() returns error the HeroListScreenState will be Error`() =
        runTest {
            `given searchUseCase invocation returns error`()

            `when initialising the ViewModel`()

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

    @Test
    fun `Success state propagates hasMorePages when the page reports no more pages`() =
        runTest {
            `given searchUseCase invocation returns results without more pages`()

            `when initialising the ViewModel`()

            `then the Success state reports hasMorePages as false`()
        }

    @Test
    fun `onReachedEnd does not fetch the next page when hasMorePages is false`() =
        runTest {
            `given searchUseCase invocation returns results without more pages`()
            `when initialising the ViewModel`()

            comicListViewModel.onReachedEnd()

            `then the use case is invoked only once`()
        }

    @Test
    fun `onReachedEnd fetches the next page when hasMorePages is true`() =
        runTest {
            `given searchUseCase invocation returns results`()
            `given searchUseCase invocation for page 2 returns results without more pages`()
            `when initialising the ViewModel`()

            comicListViewModel.onReachedEnd()

            `then the use case is invoked for page 2`()
        }

    @Test
    fun `onReachedEnd does not trigger a second fetch while one is already in progress`() =
        runTest {
            val deferred = CompletableDeferred<Result<ComicsPage>>()
            `given searchUseCase invocation returns results`()
            coEvery { comicsUseCase.invoke(2) } coAnswers { deferred.await() }
            `when initialising the ViewModel`()

            comicListViewModel.onReachedEnd()
            comicListViewModel.onReachedEnd()

            coVerify(exactly = 1) { comicsUseCase.invoke(2) }

            deferred.complete(Result.success(ComicsPage(emptyList(), hasMorePages = false)))
        }

    @Test
    fun `comics from all loaded pages are accumulated in the state`() =
        runTest {
            `given searchUseCase invocation returns results`()
            coEvery { comicsUseCase.invoke(2) } returns
                Result.success(ComicsPage(listOf(comic), hasMorePages = false))
            `when initialising the ViewModel`()

            comicListViewModel.onReachedEnd()

            `then the accumulated comic list contains items from both pages`()
        }

    @Test
    fun `RequestInProgressException does not change the current state`() =
        runTest {
            `given searchUseCase invocation returns results`()
            `when initialising the ViewModel`()

            coEvery { comicsUseCase.invoke(2) } returns
                Result.failure(RequestInProgressException("in progress"))
            comicListViewModel.onReachedEnd()

            // State must still be Success (not Error) — the real request is still running
            val state = comicListViewModel.comicsListScreenState.value.comicListState
            assertTrue(state is ComicListState.Success)
            assertTrue((state as ComicListState.Success).loadingMore)
        }

    private fun `given searchUseCase invocation returns results`() {
        coEvery { comicsUseCase.invoke(1) } returns
            Result.success(ComicsPage(SampleData.comicsSample, hasMorePages = true))
    }

    private fun `given searchUseCase invocation returns error`() {
        coEvery { comicsUseCase.invoke(1) } returns
            Result.failure(Exception())
    }

    private fun `given searchUseCase invocation returns results without more pages`() {
        coEvery { comicsUseCase.invoke(1) } returns
            Result.success(ComicsPage(SampleData.comicsSample, hasMorePages = false))
    }

    private fun `given searchUseCase invocation for page 2 returns results without more pages`() {
        coEvery { comicsUseCase.invoke(2) } returns
            Result.success(ComicsPage(emptyList(), hasMorePages = false))
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

    private fun `when a comic gets selected`() {
        comicListViewModel.comicClicked(comic)
    }

    private fun `when the selected comic gets reset`() {
        comicListViewModel.resetSelectedComic()
    }

    private fun `then searchUseCase gets invoked`() {
        coVerify { comicsUseCase.invoke(1) }
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

    private fun `then the Success state reports hasMorePages as false`() {
        val state = comicListViewModel.comicsListScreenState.value.comicListState
        assertTrue(state is ComicListState.Success)
        assertEquals(false, (state as ComicListState.Success).hasMorePages)
    }

    private fun `then the use case is invoked only once`() {
        coVerify(exactly = 1) { comicsUseCase.invoke(any()) }
    }

    private fun `then the use case is invoked for page 2`() {
        coVerify { comicsUseCase.invoke(2) }
    }

    private fun `then the accumulated comic list contains items from both pages`() {
        val state = comicListViewModel.comicsListScreenState.value.comicListState as ComicListState.Success
        assertEquals(SampleData.comicsSample.size + 1, state.comics.size)
    }
}
