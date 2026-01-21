package com.rumosoft.comics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.comics.domain.model.Comic
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
        try {
            if (fromStart) {
                currentPage = 1
            }
            viewModelScope.launch {
                getComicsUseCase(currentPage).fold(
                    onSuccess = { comicsList ->
                        parseSuccessResponse(comicsList, currentPage++)
                    },
                    onFailure = { throwable ->
                        parseErrorResponse(throwable)
                    },
                )
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Timber.e(exception, "Error loading comics: $exception")
            parseErrorResponse(exception)
        }
    }

    private fun parseSuccessResponse(comicsList: List<Comic>, page: Int) {
        setLoadingMore(false)
        _comicsListScreenState.update {
            val previousList: List<Comic> =
                if (page > 1 && it.comicListState is ComicListState.Success) {
                    it.comicListState.comics.orEmpty()
                } else {
                    emptyList()
                }
            _comicsListScreenState.value
                .copy(
                    comicListState = ComicListState.Success(
                        previousList + comicsList,
                        false,
                        ::comicClicked,
                        ::onReachedEnd,
                    ),
                )
        }
    }

    internal fun comicClicked(comic: Comic) {
        Timber.d("On comic clicked: $comic")
        viewModelScope.launch {
            _comicsListScreenState.update {
                it.copy(selectedComic = comic)
            }
        }
    }

    fun resetSelectedComic() {
        Timber.d("Reset selected comic")
        viewModelScope.launch {
            _comicsListScreenState.update {
                it.copy(selectedComic = null)
            }
        }
    }

    private fun parseErrorResponse(throwable: Throwable) {
        _comicsListScreenState.update {
            it.copy(comicListState = ComicListState.Error(throwable, ::retry))
        }
    }

    private fun onReachedEnd() {
        loadMoreData()
    }

    private fun retry() {
        loadMoreData()
    }

    private fun loadMoreData() {
        viewModelScope.launch {
            setLoadingMore(true)
        }
        loadComics(fromStart = false)
    }

    private fun setLoadingMore(value: Boolean) {
        val currentComics =
            (_comicsListScreenState.value.comicListState as? ComicListState.Success)?.comics
        if (currentComics != null) {
            _comicsListScreenState.update {
                it.copy(
                    comicListState = ComicListState.Success(
                        comics = currentComics,
                        loadingMore = value,
                    ),
                )
            }
        }
    }
}
