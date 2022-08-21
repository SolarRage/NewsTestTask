package com.myarulin.newstesttask.ui.home

import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.ui.BaseFragment.ViewEffect

interface HomeContract {

    data class HomeViewState(
        val isLoading: Boolean = false,
        val articles: List<ArticleModel> = emptyList(),
    )

    sealed interface HomeEffect : ViewEffect {

    }
}