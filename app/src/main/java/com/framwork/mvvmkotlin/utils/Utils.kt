package com.framwork.mvvmkotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import com.framwork.mvvmkotlin.MVVMApp

open class Utils {
    companion object {

        //Check For Network
        fun checkInternetConnection(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo

            return if (netInfo != null && netInfo.isConnected) {
                println("connected")
                MVVMApp.isNetworkConnected.set(true)
                true
            } else {
                println("not connected")
                MVVMApp.isNetworkConnected.set(false)
                false
            }
        }
    }
}