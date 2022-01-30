package com.rumosoft.comics.presentation.viewmodel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.comics.domain.usecase.GetComicsUseCase
import com.rumosoft.comics.presentation.viewmodel.state.ComicListScreenState
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val DEBOUNCE_DELAY = 1_000L

@ExperimentalFoundationApi
@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
) : ViewModel() {

    val comicsListScreenState: StateFlow<ComicListScreenState> get() = _comicsListScreenState
    private val _comicsListScreenState =
        MutableStateFlow(initialScreenState())

    private var performSearchJob: Job? = null

    init {
        performSearch(_comicsListScreenState.value.textSearched, true)
    }

    fun onQueryChanged(query: String) {
        if (query != _comicsListScreenState.value.textSearched) {
            Timber.d("Searching: $query")
            cancelJobIfActive()
            performSearchJob = viewModelScope.launch {
                _comicsListScreenState.update {
                    _comicsListScreenState.value
                        .copy(textSearched = query, comicListState = ComicListState.Loading)
                }
                delay(DEBOUNCE_DELAY)
                try {
                    _comicsListScreenState.update { _comicsListScreenState.value.copy(textSearched = query) }
                    performSearch(query, true)
                } catch (exception: Exception) {
                    if (exception !is CancellationException) {
                        // TODO Do something?
                    }
                }
            }
        }
    }

    fun onToggleSearchClick() {
        _comicsListScreenState.update {
            _comicsListScreenState.value
                .copy(showingSearchBar = !_comicsListScreenState.value.showingSearchBar)
        }
    }

    private fun performSearch(query: String = "", fromStart: Boolean) {
        viewModelScope.launch {
            when (val result = getComicsUseCase(query, fromStart)) {
                is Resource.Success -> {
                    parseSuccessResponse(result)
                }
                is Resource.Error -> {
                    parseErrorResponse(result)
                }
            }
        }
    }

    private suspend fun parseSuccessResponse(result: Resource.Success<List<Comic>?>) {
        setLoadingMore(false)
        _comicsListScreenState.update {
            _comicsListScreenState.value
                .copy(
                    comicListState = ComicListState.Success(
                        result.data,
                        false,
                        ::comicClicked,
                        ::onReachedEnd
                    )
                )
        }
    }

    private fun comicClicked(comic: Comic) {
        Timber.d("On comic clicked: $comic")
        viewModelScope.launch {
            _comicsListScreenState.update {
                _comicsListScreenState.value
                    .copy(selectedComic = comic)
            }
        }
    }

    fun resetSelectedComic() {
        Timber.d("Reset selected comic")
        viewModelScope.launch {
            _comicsListScreenState.update {
                _comicsListScreenState.value
                    .copy(selectedComic = null)
            }
        }
    }

    private fun onReachedEnd() {
        loadMoreData()
    }

    private suspend fun parseErrorResponse(result: Resource.Error) {
        _comicsListScreenState.update {
            _comicsListScreenState.value
                .copy(comicListState = ComicListState.Error(result.throwable, ::retry))
        }
    }

    private fun retry() {
        loadMoreData()
    }

    private fun loadMoreData() {
        viewModelScope.launch {
            setLoadingMore(true)
        }
        performSearch(_comicsListScreenState.value.textSearched, false)
    }

    private suspend fun setLoadingMore(value: Boolean) {
        val currentComics =
            (_comicsListScreenState.value.comicListState as? ComicListState.Success)?.comics
        if (currentComics != null) {
            _comicsListScreenState.update {
                _comicsListScreenState.value
                    .copy(
                        comicListState = ComicListState.Success(
                            comics = currentComics,
                            loadingMore = value,
                        )
                    )
            }
        }
    }

    private fun cancelJobIfActive() {
        performSearchJob?.takeIf { it.isActive }?.cancel()
    }

    private fun initialScreenState(): ComicListScreenState =
        ComicListScreenState(ComicListState.Loading)
}
