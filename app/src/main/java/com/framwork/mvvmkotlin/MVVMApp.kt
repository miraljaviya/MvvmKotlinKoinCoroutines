package com.framwork.mvvmkotlin

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.framwork.mvvmkotlin.di.coreModule
import com.framwork.mvvmkotlin.di.networkModule
import com.framwork.mvvmkotlin.di.persistenceModule
import com.framwork.mvvmkotlin.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

//Application Class
class MVVMApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MVVMApp)
            androidLogger()
            modules(listOf(coreModule, vmModule, persistenceModule, networkModule))
        }
    }

    companion object{
        var isNetworkConnected: ObservableBoolean = ObservableBoolean(false)
    }
}