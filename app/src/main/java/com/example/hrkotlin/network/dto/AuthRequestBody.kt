package com.example.hrkotlin.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthRequestBody(
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null
) : Serializable
