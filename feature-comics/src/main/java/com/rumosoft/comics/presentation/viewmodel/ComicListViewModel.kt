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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalFoundationApi
@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase,
) : ViewModel() {

    val comicsListScreenState: StateFlow<ComicListScreenState> get() = _comicsListScreenState
    private val _comicsListScreenState =
        MutableStateFlow(initialScreenState())

    init {
        performSearch(_comicsListScreenState.value.textSearched, true)
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
        _comicsListScreenState.emit(
            _comicsListScreenState.value
                .copy(
                    comicListState = ComicListState.Success(
                        result.data,
                        false,
                        {},
                        {}
                    )
                )
        )
    }

    private suspend fun parseErrorResponse(result: Resource.Error) {
        _comicsListScreenState.emit(
            _comicsListScreenState.value
                .copy(comicListState = ComicListState.Error(result.throwable, ::retry))
        )
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
        val currentComics = (_comicsListScreenState.value.comicListState as? ComicListState.Success)?.comics
        if (currentComics != null) {
            _comicsListScreenState.emit(
                _comicsListScreenState.value
                    .copy(
                        comicListState = ComicListState.Success(
                            comics = currentComics,
                            loadingMore = value,
                        )
                    )
            )
        }
    }

    private fun initialScreenState(): ComicListScreenState =
        ComicListScreenState(ComicListState.Loading)
}
