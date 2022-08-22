package com.myarulin.newstesttask.api

import com.myarulin.newstesttask.api.responses.NewsResponseItem
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    fun getBreakingNews(
        @Query("country")
        countryCode: String = "ua",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Single<NewsResponseItem>

    @GET("v2/everything")
    fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Single<NewsResponseItem>

    companion object{
        const val API_KEY = "28f98597964c4b3c9241437a4e8f2627"
        const val BASE_URL = "https://newsapi.org"
    }
}