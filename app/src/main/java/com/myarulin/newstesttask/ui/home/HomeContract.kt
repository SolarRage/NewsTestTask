package com.myarulin.newstesttask.ui.home

import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.ui.BaseFragment.ViewEffect

interface HomeContract {

    data class HomeViewState(
        val isLoading: Boolean = false,
        val articles: List<Article> = emptyList(),
    )

    sealed interface HomeEffect : ViewEffect {

    }
}