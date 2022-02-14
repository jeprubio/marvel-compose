package com.rumosoft.comics.presentation.viewmodel.state

import androidx.compose.foundation.ExperimentalFoundationApi
import com.rumosoft.commons.domain.model.Comic

@ExperimentalFoundationApi
data class ComicListScreenState(
    val comicListState: ComicListState,
    val showingSearchBar: Boolean = false,
    val selectedComic: Comic? = null,
    val textSearched: String = "",
)

@ExperimentalFoundationApi
sealed class ComicListState {
    companion object {
        const val ProgressIndicator = "progressIndicator"
        const val ErrorResult = "errorResult"
        const val SuccessResult = "successResult"
        const val NoResults = "noResults"
        const val RetryTag = "retry"
    }

    object Loading : ComicListState()

    class Error(
        val throwable: Throwable,
        val retry: () -> Unit = {},
    ) : ComicListState()

    class Success(
        val comics: List<Comic>?,
        val loadingMore: Boolean = false,
        val onClick: (Comic) -> Unit = {},
        val onEndReached: () -> Unit = {},
    ) : ComicListState()
}
