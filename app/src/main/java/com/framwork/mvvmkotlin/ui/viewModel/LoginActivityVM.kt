package com.framwork.mvvmkotlin.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.framwork.mvvmkotlin.DefaultDispatcherProvider
import com.framwork.mvvmkotlin.DispatcherProvider
import com.framwork.mvvmkotlin.base.BaseVM
import com.framwork.mvvmkotlin.dataBase.User
import com.framwork.mvvmkotlin.dataBase.UserDao

import com.framwork.mvvmkotlin.rest.ApiInterface
import com.framwork.mvvmkotlin.rest.error.ErrorClass
import com.framwork.mvvmkotlin.state.ScreenState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException
import java.util.regex.Pattern

class LoginActivityVM(private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()) : BaseVM() {

    private val emailPattern: Pattern by inject()
    private val apiService: ApiInterface by inject()
    private val userDao : UserDao by inject()
    val emailLiveData = MutableLiveData<String>("eve.holt@reqres.in")
    val passwordLiveData = MutableLiveData<String>("cityslicka")
    private val state = MutableLiveData<ScreenState>(ScreenState.Initial)

    fun state(): LiveData<ScreenState> = state

    fun login() = viewModelScope.launch(dispatchers.default()) {

       val email: String? = emailLiveData.value
       val password: String? = passwordLiveData.value

        if (email!!.isBlank()) {
            state.postValue(ScreenState.EmailValidationError("Email cannot be empty"))
            return@launch
        } else if (!emailPattern.matcher(email).matches()) {
            state.postValue(ScreenState.EmailValidationError("Invalid email address"))
            return@launch
        }

        if (password!!.isBlank()) {
            state.postValue(ScreenState.PasswordValidationError("Password cannot be empty"))
            return@launch
        } else if (password.length !in 8..20) {
            state.postValue(ScreenState.PasswordValidationError("Password must be 8 to 20 characters long!"))
            return@launch
        }

        state.postValue(ScreenState.Loading)
        runCatching {
            apiService.getLogin(email, password)
        }.fold({
            state.postValue(ScreenState.LoginSuccess(it))
            userDao.insertAll(User(0,email,password))
        }, {
            val error = when (it) {
                is HttpException -> {
                    it.response()?.errorBody()?.string()?.let { error ->
                        Gson().fromJson(error, ErrorClass::class.java).error
                    } ?: "something went wrong"
                }
                is Exception -> {
                       it.printStackTrace()
                }
                is Error ->{
                    it.printStackTrace()
                }
                else -> "Something went wrong!"
            }
            state.postValue(ScreenState.LoginFailure(error.toString()))
        })
    }


}


