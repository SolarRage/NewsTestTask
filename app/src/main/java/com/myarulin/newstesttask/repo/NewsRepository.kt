package com.myarulin.newstesttask.repo

import com.myarulin.newstesttask.api.NetService
import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.db.NewsDatabase
import com.myarulin.newstesttask.model.ArticleModel

class NewsRepository(
    val db: NewsDatabase
) {
    val api = NetService.configureRetrofit()
    fun getNews(countyCode: String, pageNumber: Int) =
        api.getBreakingNews(countyCode)

    fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchForNews(searchQuery)

    fun upsert(article: ArticleModel) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAll()

    fun searchSavedNews(search: String) = db.getArticleDao().searchArticle(search)

    fun deleteArticle(article: ArticleModel) = db.getArticleDao().deleteArtcile(article)

    fun checkBookmark(id: Int?) = db.getArticleDao().isItemExists(id)

}