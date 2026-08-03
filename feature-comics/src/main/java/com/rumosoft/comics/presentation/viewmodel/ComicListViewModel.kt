package com.rumosoft.comics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.domain.model.ComicsPage
import com.rumosoft.comics.domain.usecase.GetComicsUseCase
import com.rumosoft.comics.presentation.viewmodel.state.ComicListScreenState
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
) : ViewModel() {

    val comicsListScreenState: StateFlow<ComicListScreenState> get() = _comicsListScreenState
    private val _comicsListScreenState =
        MutableStateFlow(ComicListScreenState(ComicListState.Loading))
    private var currentPage = 1

    init {
        loadComics()
    }

    private fun loadComics(fromStart: Boolean = true) {
        if (fromStart) {
            currentPage = 1
        }
        viewModelScope.launch {
            try {
                getComicsUseCase(currentPage).fold(
                    onSuccess = { comicsPage ->
                        parseSuccessResponse(comicsPage, currentPage)
                        currentPage++
                    },
                    onFailure = { throwable ->
                        parseErrorResponse(throwable)
                    },
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                Timber.e(exception, "Error loading comics: $exception")
                parseErrorResponse(exception)
            }
        }
    }

    private fun parseSuccessResponse(comicsPage: ComicsPage, page: Int) {
        setLoadingMore(false)
        _comicsListScreenState.update {
            val previousList: List<Comic> =
                if (page > 1 && it.comicListState is ComicListState.Success) {
                    it.comicListState.comics
                } else {
                    emptyList()
                }
            it.copy(
                comicListState = ComicListState.Success(
                    comics = previousList + comicsPage.comics,
                    loadingMore = false,
                    hasMorePages = comicsPage.hasMorePages,
                ),
            )
        }
    }

    internal fun comicClicked(comic: Comic) {
        Timber.d("On comic clicked: $comic")
        _comicsListScreenState.update { it.copy(selectedComic = comic) }
    }

    fun resetSelectedComic() {
        Timber.d("Reset selected comic")
        _comicsListScreenState.update { it.copy(selectedComic = null) }
    }

    private fun parseErrorResponse(throwable: Throwable) {
        _comicsListScreenState.update {
            it.copy(comicListState = ComicListState.Error(throwable))
        }
    }

    fun onReachedEnd() {
        val current =
            _comicsListScreenState.value.comicListState as? ComicListState.Success ?: return
        if (!current.hasMorePages || current.loadingMore) return
        setLoadingMore(true)
        loadComics(fromStart = false)
    }

    fun retry() {
        _comicsListScreenState.update { it.copy(comicListState = ComicListState.Loading) }
        loadComics(fromStart = false)
    }

    private fun setLoadingMore(value: Boolean) {
        _comicsListScreenState.update { current ->
            val successState = current.comicListState as? ComicListState.Success
                ?: return@update current
            current.copy(comicListState = successState.copy(loadingMore = value))
        }
    }
}
