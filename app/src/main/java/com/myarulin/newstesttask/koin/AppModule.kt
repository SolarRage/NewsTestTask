package com.myarulin.newstesttask.koin

import androidx.room.Room
import com.myarulin.newstesttask.db.NewsDatabase
import com.myarulin.newstesttask.repo.NewsRepository
import com.myarulin.newstesttask.ui.bookmarks.BookmarkViewModel
import com.myarulin.newstesttask.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            NewsDatabase::class.java,
            "article_db.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { NewsRepository(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
}
