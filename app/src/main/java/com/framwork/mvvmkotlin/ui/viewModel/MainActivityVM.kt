package com.framwork.mvvmkotlin.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.framwork.mvvmkotlin.DefaultDispatcherProvider
import com.framwork.mvvmkotlin.DispatcherProvider
import com.framwork.mvvmkotlin.base.BaseVM
import com.framwork.mvvmkotlin.rest.ApiInterface
import com.framwork.mvvmkotlin.rest.error.ErrorClass
import com.framwork.mvvmkotlin.state.ScreenState
import com.framwork.mvvmkotlin.ui.adapter.UserAdapter
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException

class MainActivityVM(private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()) : BaseVM()  {

    private val apiService: ApiInterface by inject()
    private val state = MutableLiveData<ScreenState>(ScreenState.Initial)
    val userAdapter: UserAdapter = UserAdapter()

    fun state(): LiveData<ScreenState> = state

    fun getUsers() = viewModelScope.launch(dispatchers.default()) {

        state.postValue(ScreenState.Loading)
        runCatching {
            apiService.getUsersData(1)
        }.fold({
            state.postValue(ScreenState.UserSuccess(it))
        }, {
            val error = when (it) {
                is HttpException -> {
                    it.response()?.errorBody()?.string()?.let { error ->
                        Gson().fromJson(error, ErrorClass::class.java).error
                    } ?: "something went wrong"
                }
                else -> "Something went wrong!"
            }
            state.postValue(ScreenState.LoginFailure(error))
        })
    }
}