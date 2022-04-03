package com.rumosoft.characters.presentation.viewmodel.state

import com.rumosoft.commons.domain.model.Character

sealed class DetailsState {
    object Loading : DetailsState()
    class Success(val character: Character) : DetailsState()
}
