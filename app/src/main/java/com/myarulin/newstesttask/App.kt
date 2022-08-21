package com.myarulin.newstesttask

import android.app.Application
import com.myarulin.newstesttask.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(appModule)
        }
    }
}