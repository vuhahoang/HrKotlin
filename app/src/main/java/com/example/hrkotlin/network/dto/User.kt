package com.example.hrkotlin.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    @SerializedName("fullName") var fullName: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null
) : Serializable