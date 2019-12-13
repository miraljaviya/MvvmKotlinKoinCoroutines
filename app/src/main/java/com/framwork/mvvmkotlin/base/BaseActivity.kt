package com.framwork.mvvmkotlin.base

import android.content.*
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.framwork.mvvmkotlin.BR
import com.framwork.mvvmkotlin.dataBase.UserDao
import com.framwork.mvvmkotlin.rest.ApiInterface
import com.framwork.mvvmkotlin.utils.Utils
import org.koin.android.ext.android.inject

abstract class BaseActivity<VM : BaseVM, VDB : ViewDataBinding>(@LayoutRes private val layoutID: Int) :
    AppCompatActivity() {

    abstract val viewModel: VM
    private lateinit var binding: VDB
    val sharedPreferences: SharedPreferences by inject()
    val sharedPreferencesEdit: SharedPreferences.Editor by inject()
    val mContext: Context by inject()
    val userDao: UserDao by inject()
    val apiInterface: ApiInterface by inject()

    val connectivity: String = "android.net.conn.CONNECTIVITY_CHANGE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutID)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
    }

    //implement receiver
    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            when (intent?.action) {
                connectivity -> context?.let { Utils.checkInternetConnection(it) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(myReceiver, IntentFilter(connectivity))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myReceiver)
    }

}