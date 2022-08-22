package com.myarulin.newstesttask.repo

import com.myarulin.newstesttask.api.NetService
import com.myarulin.newstesttask.db.Article
import com.myarulin.newstesttask.db.NewsDatabase

class NewsRepository(
    val db: NewsDatabase
) {
    val api = NetService.configureRetrofit()
    fun getNews(countyCode: String, pageNumber: Int) =
        api.getBreakingNews(countyCode)

    fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchForNews(searchQuery)

    fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAll()

}