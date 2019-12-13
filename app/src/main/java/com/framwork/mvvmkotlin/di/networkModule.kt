package com.framwork.mvvmkotlin.di

import com.framwork.mvvmkotlin.rest.ApiInterface
import com.framwork.mvvmkotlin.utils.Constants
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single {
        // 2
        okHttp()  // 3
    }
    single {
        retrofit(Constants.BASE_URL)  // 4
    }
    single {
        get<Retrofit>().create(ApiInterface::class.java)   // 5
    }
}
private fun okHttp() = OkHttpClient.Builder().build()
private fun retrofit(baseUrl: String) = Retrofit.Builder()
    .callFactory(OkHttpClient.Builder().build())
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()



