package com.example.hrkotlin.network

import com.example.hrkotlin.network.dto.AuthRequestBody
import com.example.hrkotlin.network.dto.AuthenData
import com.example.hrkotlin.network.dto.ResponseDTO
import com.example.hrkotlin.network.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OttService {
    @POST("/api/v1/auth/login")
    fun login(@Body body: AuthRequestBody?): Call<ResponseDTO<AuthenData?>?>?

    @GET("/api/v1/users/me")
    fun getMe() : Call<ResponseDTO<User>>
}