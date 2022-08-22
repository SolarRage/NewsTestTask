package com.myarulin.newstesttask.koin

import com.myarulin.newstesttask.api.NetService
import com.myarulin.newstesttask.db.NewsDatabase
import com.myarulin.newstesttask.repo.NewsRepository
import com.myarulin.newstesttask.ui.bookmarks.BookmarkViewModel
import com.myarulin.newstesttask.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { NewsDatabase.createDatabase(androidContext()) }

    single { NetService.configureRetrofit() }

    single { NewsRepository(get(), get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
}
