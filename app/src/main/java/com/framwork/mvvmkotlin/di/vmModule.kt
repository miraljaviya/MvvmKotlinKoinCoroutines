package com.framwork.mvvmkotlin.di

import com.framwork.mvvmkotlin.ui.viewModel.LoginActivityVM
import com.framwork.mvvmkotlin.ui.viewModel.MainActivityVM
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { LoginActivityVM() }
    viewModel { MainActivityVM() }

}