package com.myarulin.newstesttask.ui.bookmarks

import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.ui.BaseFragment

interface BookmarksContract {

    data class BookmarksViewState(
        val isLoading: Boolean = false,
        val articles: List<Article> = emptyList(),
    )

    sealed interface BookmarksEffect : BaseFragment.ViewEffect {

    }
}