package com.myarulin.newstesttask.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "articles"
)
data class ArticleModel(
    @PrimaryKey(autoGenerate = true)
    val newsId: Int? = 0,
    val title: String?,
    val description: String?,
    val website: String?,
    val imageURL: String?,
)
