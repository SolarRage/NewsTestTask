package com.myarulin.newstesttask.repo

import com.myarulin.newstesttask.api.NewsApi
import com.myarulin.newstesttask.db.NewsDatabase
import com.myarulin.newstesttask.model.ArticleModel
import com.myarulin.newstesttask.model.toDatabaseModel
import io.reactivex.Completable

class NewsRepository(
    private val db: NewsDatabase,
    private val api: NewsApi
) {

    fun getNews(countyCode: String, pageNumber: Int) =
        api.getBreakingNews(countyCode)

    fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchForNews(searchQuery)

    fun upsert(article: ArticleModel): Completable {
        return db.getArticleDao().upsert(article.toDatabaseModel())
    }

    fun getSavedNews() = db.getArticleDao().getAll()

    fun searchSavedNews(search: String) = db.getArticleDao().searchArticle(search)

    fun deleteArticle(article: ArticleModel) = db.getArticleDao()
        .deleteArticle(article.toDatabaseModel())

    fun isItemExists(website: String) = db.getArticleDao().isItemExists(website)
}