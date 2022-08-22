package com.myarulin.newstesttask.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val website: String,
    val title: String?,
    val description: String?,
    val imageURL: String?
)
