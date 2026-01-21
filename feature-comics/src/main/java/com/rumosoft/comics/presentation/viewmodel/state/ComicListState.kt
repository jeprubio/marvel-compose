package com.rumosoft.comics.presentation.viewmodel.state

import com.rumosoft.comics.domain.model.Comic

data class ComicListScreenState(
    val comicListState: ComicListState,
    val selectedComic: Comic? = null,
)

sealed class ComicListState {
    companion object {
        const val ProgressIndicator = "progressIndicator"
        const val ErrorResult = "errorResult"
        const val SuccessResult = "successResult"
        const val NoResults = "noResults"
        const val RetryTag = "retry"
    }

    object Loading : ComicListState()

    data class Error(
        val throwable: Throwable,
    ) : ComicListState()

    data class Success(
        val comics: List<Comic>?,
        val loadingMore: Boolean = false,
        val hasMorePages: Boolean = true,
    ) : ComicListState()
}
