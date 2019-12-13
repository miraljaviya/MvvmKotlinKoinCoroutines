package com.framwork.mvvmkotlin.di

import androidx.room.Room
import com.framwork.mvvmkotlin.dataBase.AppDatabase
import com.framwork.mvvmkotlin.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, Constants.PREF_NAME)
            .allowMainThreadQueries()
            .build()
    }

    //if want more dao then declare them like below :)
    single { get<AppDatabase>().userDao() }
}