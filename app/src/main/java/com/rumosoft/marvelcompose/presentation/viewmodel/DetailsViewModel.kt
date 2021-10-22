package com.rumosoft.marvelcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.presentation.viewmodel.state.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor() : ViewModel() {

    val detailsState: StateFlow<DetailsState> get() = _detailsState
    private val _detailsState =
        MutableStateFlow(initialDetailsState())

    fun setHero(hero: Hero) {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.Success(hero))
        }
    }

    private fun initialDetailsState(): DetailsState = DetailsState.Loading
}
