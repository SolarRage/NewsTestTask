package com.myarulin.newstesttask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myarulin.newstesttask.db.entities.ArticleEntity
import com.myarulin.newstesttask.model.ArticleModel

@Database(
    entities = [ArticleEntity::class],
    version = 2
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getArticleDao(): NewsDao

    companion object {

        fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "article_db.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}