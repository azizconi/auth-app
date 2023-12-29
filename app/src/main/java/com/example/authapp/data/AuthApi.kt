package com.example.authapp.data

import com.example.authapp.data.model.response.code.CodeResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {


    @GET("getCode")
    suspend fun getCode(
        @Query("login") login: String
    ): Response<CodeResponseModel>

    @GET("getToken")
    suspend fun getToken(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<String>

    @GET("regenerateCode")
    suspend fun regenerateCode(
        @Query("login") login: String,
    ): Response<String>


}