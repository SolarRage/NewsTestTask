package com.myarulin.newstesttask.repo

import com.myarulin.newstesttask.api.NetService
import com.myarulin.newstesttask.db.NewsDatabase
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.model.toDatabaseModel

class NewsRepository(
    val db: NewsDatabase
) {
    val api = NetService.configureRetrofit()
    fun getNews(countyCode: String, pageNumber: Int) =
        api.getBreakingNews(countyCode)

    fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchForNews(searchQuery)

    fun upsert(article: ArticleModel) = db.getArticleDao().upsert(article.toDatabaseModel())

    fun getSavedNews() = db.getArticleDao().getAll()

    fun searchSavedNews(search: String) = db.getArticleDao().searchArticle(search)

    fun deleteArticle(article: ArticleModel) = db.getArticleDao()
        .deleteArticle(article.toDatabaseModel())

    fun isItemExists(website: String) = db.getArticleDao().isItemExists(website)

    fun subscribeForChanges() = db.getArticleDao().subscribeForChanges()
}