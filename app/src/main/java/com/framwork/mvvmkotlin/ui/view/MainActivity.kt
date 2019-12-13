package com.framwork.mvvmkotlin.ui.view


import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.framwork.mvvmkotlin.R
import com.framwork.mvvmkotlin.base.BaseActivity
import com.framwork.mvvmkotlin.databinding.MainActivityBinding
import com.framwork.mvvmkotlin.state.ScreenState
import com.framwork.mvvmkotlin.ui.viewModel.MainActivityVM
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainActivityVM, MainActivityBinding>(R.layout.activity_main){

    override val viewModel: MainActivityVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(userDao.getAllData.isEmpty()){
            viewModel.getUsers()
        }else{
            viewModel.userAdapter.updateUserList(userDao.getAllData)
            viewModel.userAdapter.notifyDataSetChanged()
        }

        viewModel.state().observe(this) { state ->
            renderState(state)
        }
    }

    private fun renderState(state: ScreenState) = state.run {
        when (state) {

            ScreenState.Loading -> {
                enableViews(false)
            }
            is ScreenState.UserSuccess ->{
                enableViews(true)
                for(i in state.userResponse.data.indices){
                    userDao.insertAllData(state.userResponse.data[i])
                }
                viewModel.userAdapter.updateUserList(state.userResponse.data)
                viewModel.userAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun enableViews(enable: Boolean) {
        recyclerView.visibility = if (enable) View.VISIBLE else View.GONE
        progressbar.visibility = if (enable) View.INVISIBLE else View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        viewModel.state().removeObservers(this)
    }

}
