package com.rumosoft.comics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.comics.domain.usecase.GetComicsUseCase
import com.rumosoft.comics.presentation.viewmodel.state.ComicListScreenState
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
) : ViewModel() {
    companion object {
        const val DEBOUNCE_DELAY = 1_000L
    }

    val comicsListScreenState: StateFlow<ComicListScreenState> get() = _comicsListScreenState
    private val _comicsListScreenState =
        MutableStateFlow(initialScreenState())
    private val textSearched = MutableStateFlow("")

    init {
        observeTextSearched()
    }

    fun onQueryChanged(query: String) {
        if (query != _comicsListScreenState.value.textSearched) {
            textSearched.value = query
        }
    }

    fun onToggleSearchClick() {
        _comicsListScreenState.update {
            _comicsListScreenState.value
                .copy(showingSearchBar = !_comicsListScreenState.value.showingSearchBar)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeTextSearched() {
        textSearched
            .debounce(DEBOUNCE_DELAY)
            .onEach { searching ->
                _comicsListScreenState.update { it.copy(textSearched = searching) }
                performSearch(searching, fromStart = true)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = "",
            )
    }

    private fun performSearch(query: String = "", fromStart: Boolean) {
        try {
            Timber.d("Searching: $query")
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
        } catch (exception: Exception) {
            if (exception !is CancellationException) {
                // TODO Do something?
            }
        }
    }

    private fun parseSuccessResponse(result: Resource.Success<List<Comic>?>) {
        setLoadingMore(false)
        _comicsListScreenState.update {
            _comicsListScreenState.value
                .copy(
                    comicListState = ComicListState.Success(
                        result.data,
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

    private fun parseErrorResponse(result: Resource.Error) {
        _comicsListScreenState.update {
            it.copy(comicListState = ComicListState.Error(result.throwable, ::retry))
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
        performSearch(_comicsListScreenState.value.textSearched, fromStart = false)
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

    private fun initialScreenState(): ComicListScreenState =
        ComicListScreenState(ComicListState.Loading)
}
