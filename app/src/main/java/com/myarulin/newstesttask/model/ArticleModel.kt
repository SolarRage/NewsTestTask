package com.myarulin.newstesttask.model

import com.myarulin.newstesttask.db.entities.ArticleEntity


data class ArticleModel(
    val website: String,
    val title: String?,
    val description: String?,
    val imageURL: String?,
    val isSaved: Boolean
)

fun ArticleModel.toDatabaseModel() = ArticleEntity(
    website,
    title,
    description,
    imageURL
)

fun ArticleEntity.toDomainModel() = ArticleModel(
    website,
    title,
    description,
    imageURL,
    true
)
