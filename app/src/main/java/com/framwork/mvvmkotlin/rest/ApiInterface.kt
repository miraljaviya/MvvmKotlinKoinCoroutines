package com.framwork.mvvmkotlin.rest

import com.framwork.mvvmkotlin.rest.voResponse.LoginResponse
import com.framwork.mvvmkotlin.rest.voResponse.UserResponse
import retrofit2.http.*

interface ApiInterface {

    @GET("users?")
    suspend fun getUsersData(@Query("page") page: Int): UserResponse

    @POST("login")
    @FormUrlEncoded
    suspend fun getLogin(@Field("email") email: String, @Field("password") pass: String):LoginResponse
}