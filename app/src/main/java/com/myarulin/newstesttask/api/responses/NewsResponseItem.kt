package com.myarulin.newstesttask.api.responses

import com.myarulin.newstesttask.db.Article

data class NewsResponseItem(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)
