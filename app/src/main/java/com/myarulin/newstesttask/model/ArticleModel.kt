package com.myarulin.newstesttask.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles")
data class ArticleModel constructor(
    @PrimaryKey
    val website: String,
    val title: String?,
    val description: String?,
    val imageURL: String?,
)
