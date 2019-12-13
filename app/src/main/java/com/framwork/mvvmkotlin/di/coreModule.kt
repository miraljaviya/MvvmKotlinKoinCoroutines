package com.framwork.mvvmkotlin.di

import android.content.Context
import android.util.Patterns
import com.framwork.mvvmkotlin.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.regex.Pattern

val coreModule = module {
    single {
        androidContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    }

    single {
        androidContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE).edit()
    }

    single<Pattern> {
        Patterns.EMAIL_ADDRESS
    }
}