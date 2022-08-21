package com.myarulin.newstesttask.repo

import com.myarulin.newstesttask.api.NetService
import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.db.NewsDatabase

class NewsRepository(
    val db: NewsDatabase
) {
    val api = NetService.configureRetrofit()
    fun getBreakingNews(countyCode: String) =
        api.getBreakingNews(countyCode)

    fun searchNews(searchQuery: String) =
        api.searchForNews(searchQuery)

    fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    fun deleteArticle(article: Article) = db.getArticleDao().deleteArtcile(article)
}