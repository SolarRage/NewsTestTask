package com.myarulin.newstesttask.api

import com.google.gson.GsonBuilder
import com.myarulin.newstesttask.api.NewsApi.Companion.BASE_URL
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetService {

    companion object {
        fun configureRetrofit(): NewsApi {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
                )
                .addConverterFactory(
                    GsonConverterFactory.create(gson)
                )
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(NewsApi::class.java)
        }
    }
}