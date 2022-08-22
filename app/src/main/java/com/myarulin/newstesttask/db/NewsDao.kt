package com.myarulin.newstesttask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(article: Article):Single<Long>

    @Query("SELECT * FROM articles")
    fun getAll(): Single<List<Article>>

    @Query("SELECT * FROM articles WHERE title LIKE '%' || :search || '%'")
    fun searchArticle(search: String?): Single<List<Article>>
}