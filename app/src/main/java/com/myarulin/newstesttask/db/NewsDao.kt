package com.myarulin.newstesttask.db

import androidx.room.*
import com.myarulin.newstesttask.db.entities.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(article: ArticleEntity): Completable

    @Query("SELECT * FROM articles")
    fun getAll(): Single<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE title LIKE '%' || :search || '%'")
    fun searchArticle(search: String?): Single<List<ArticleEntity>>

    @Delete
    fun deleteArticle(article: ArticleEntity): Completable

    @Query("SELECT EXISTS(SELECT * FROM articles WHERE website = :website)")
    fun isItemExists(website: String): Single<Boolean>
}