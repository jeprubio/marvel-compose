package com.rumosoft.comics.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.comics.domain.usecase.GetComicDetailsUseCase
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getComicDetailsUseCase: GetComicDetailsUseCase,
) : ViewModel() {
    private val comicId: Int = savedStateHandle["comicId"]!!

    val detailsState: StateFlow<ComicDetailsState> get() = _detailsState
    private val _detailsState =
        MutableStateFlow<ComicDetailsState>(ComicDetailsState.Loading)

    init {
        setComic(comicId)
    }

    private fun setComic(comicId: Int) {
        viewModelScope.launch {
            getComicDetailsUseCase(comicId).fold(
                onSuccess = { comic ->
                    _detailsState.update { ComicDetailsState.Success(comic) }
                },
                onFailure = { throwable ->
                    _detailsState.update { ComicDetailsState.Error(throwable) }
                },
            )
        }
    }
}
