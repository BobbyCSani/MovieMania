package com.project.moviemania.service.manager

import com.project.moviemania.utility.StaticValues
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseApiManager {

    companion object{
        private const val baseUrl = StaticValues.BASE_API_URL
        val client: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        }
        private val builder by lazy {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
        }
        private val retrofit by lazy { builder.build() }

        fun <T> buildService(type: Class<T>) : T{
            return retrofit.create(type)
        }
    }
}