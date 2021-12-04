package com.rumosoft.comics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.comics.domain.usecase.GetComicDetailsUseCase
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.commons.infrastructure.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailsViewModel @Inject constructor(
    private val getComicDetailsUseCase: GetComicDetailsUseCase,
) : ViewModel() {

    val detailsState: StateFlow<ComicDetailsState> get() = _detailsState
    private val _detailsState =
        MutableStateFlow<ComicDetailsState>(ComicDetailsState.Loading)

    fun fetchComicData(comicId: Int) {
        viewModelScope.launch {
            val results = getComicDetailsUseCase(comicId)
            when (results) {
                is Resource.Success -> {
                    _detailsState.emit(ComicDetailsState.Success(results.data))
                }
                is Resource.Error -> {
                    _detailsState.emit(ComicDetailsState.Error(results.throwable))
                }
            }
        }
    }
}
