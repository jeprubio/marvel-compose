package com.rumosoft.comics.presentation.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import com.rumosoft.comics.TestCoroutineExtension
import com.rumosoft.comics.domain.usecase.GetComicsUseCase
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.commons.infrastructure.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
internal class ComicListViewModelTest {
    private val comicsUseCase: GetComicsUseCase = mockk()
    private lateinit var comicListViewModel: ComicListViewModel

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

    private fun `given searchUseCase invocation returns results`() {
        coEvery { comicsUseCase.invoke("", true) } returns
            Resource.Success(SampleData.comicsSample)
    }

    private fun `given searchUseCase invocation returns error`() {
        coEvery { comicsUseCase.invoke("", true) } returns
            Resource.Error(Exception())
    }

    private fun `when initialising the ViewModel`() {
        comicListViewModel = ComicListViewModel(comicsUseCase)
    }

    private fun `then searchUseCase gets invoked`() {
        coVerify { comicsUseCase.invoke("", true) }
    }

    private fun `then comicListState should be Success`() {
        TestCase.assertTrue(comicListViewModel.comicsListScreenState.value.comicListState is ComicListState.Success)
    }

    private fun `then comicListState should be Error`() {
        TestCase.assertTrue(comicListViewModel.comicsListScreenState.value.comicListState is ComicListState.Error)
    }
}
