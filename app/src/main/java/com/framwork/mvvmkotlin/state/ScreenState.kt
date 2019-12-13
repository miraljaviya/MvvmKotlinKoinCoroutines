package com.framwork.mvvmkotlin.state

import com.framwork.mvvmkotlin.rest.voResponse.LoginResponse
import com.framwork.mvvmkotlin.rest.voResponse.UserResponse

sealed class ScreenState {
    object Initial : ScreenState()
    data class LoginSuccess(val response: LoginResponse) : ScreenState()
    data class LoginFailure(val error: String) : ScreenState()
    object Loading : ScreenState()
    data class EmailValidationError(val error: String) : ScreenState()
    data class PasswordValidationError(val error: String) : ScreenState()
    data class UserSuccess(val userResponse: UserResponse) : ScreenState()

}