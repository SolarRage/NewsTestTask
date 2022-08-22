package com.myarulin.newstesttask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myarulin.newstesttask.model.ArticleModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(article: ArticleModel):Completable

    @Query("SELECT * FROM articles")
    fun getAll(): Single<List<ArticleModel>>

    @Query("SELECT * FROM articles WHERE title LIKE '%' || :search || '%'")
    fun searchArticle(search: String?): Single<List<ArticleModel>>
}