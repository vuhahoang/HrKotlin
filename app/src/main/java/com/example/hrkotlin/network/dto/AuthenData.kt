package com.example.hrkotlin.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AuthenData(
    @SerializedName("accessToken") var accessToken: String? = null,
    @SerializedName("refreshToken") var refreshToken: String? = null,
    @SerializedName("tokenType") var tokenType: String? = null
) : Serializable

