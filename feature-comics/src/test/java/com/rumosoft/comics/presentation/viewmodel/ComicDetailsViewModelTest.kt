package com.rumosoft.comics.presentation.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import com.rumosoft.comics.domain.usecase.GetComicDetailsUseCase
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.library_tests.TestCoroutineExtension
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
    private val viewModel: ComicDetailsViewModel = ComicDetailsViewModel(comicDetailsUseCase)
    private val comicId = 123

    @Test
    fun `fetchComicData() calls getComicDetailsUseCase`() {
        runTest {
            `given use case invocation returns results`()

            `when fetchComicData gets invoked in viewmodel`()

            `then use case gets invoked`()
        }
    }

    @Test
    fun `if fetchComicData() goes well the detailsState should be Success`() {
        runTest {
            `given use case invocation returns results`()

            `when fetchComicData gets invoked in viewmodel`()

            `then detailsState is Success`()
        }
    }

    @Test
    fun `if fetchComicData() returns error the detailsState should be Error`() {
        runTest {
            `given use case invocation returns error`()

            `when fetchComicData gets invoked in viewmodel`()

            `then detailsState is Error`()
        }
    }

    private fun `given use case invocation returns results`() {
        coEvery { comicDetailsUseCase.invoke(comicId) } returns Resource.Success(SampleData.comicsSample.first())
    }

    private fun `given use case invocation returns error`() {
        coEvery { comicDetailsUseCase.invoke(comicId) } returns Resource.Error(Exception("Error"))
    }

    private fun `when fetchComicData gets invoked in viewmodel`() {
        viewModel.fetchComicData(comicId)
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
