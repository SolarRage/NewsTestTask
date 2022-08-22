package com.myarulin.newstesttask.ui.bookmarks

import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.ui.BaseFragment

interface BookmarksContract {

    data class BookmarksViewState(
        val isLoading: Boolean = false,
        val articles: List<ArticleModel> = emptyList(),
    )

    sealed interface BookmarksEffect : BaseFragment.ViewEffect {

    }
}