package com.rumosoft.characters.presentation.viewmodel.state

import com.rumosoft.characters.domain.model.Character

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Error(val throwable: Throwable) : DetailsState()
    data class Success(val character: Character) : DetailsState()
}
