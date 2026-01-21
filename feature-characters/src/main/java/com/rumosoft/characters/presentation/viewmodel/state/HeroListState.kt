package com.rumosoft.characters.presentation.viewmodel.state

import com.rumosoft.characters.domain.model.Character

const val HeroListProgressIndicator = "progressIndicator"
const val HeroListErrorResult = "errorResult"
const val HeroListSuccessResult = "successResult"
const val HeroListNoResults = "noResults"

data class HeroListScreenState(
    val heroListState: HeroListState,
    val selectedCharacter: Character? = null,
)

sealed class HeroListState {
    object Loading : HeroListState()

    class Error(
        val throwable: Throwable,
        val retry: () -> Unit = {},
    ) : HeroListState()

    class Success(
        val characters: List<Character>?,
        val loadingMore: Boolean = false,
        val onClick: (Character) -> Unit = {},
        val onEndReached: () -> Unit = {},
    ) : HeroListState()
}
