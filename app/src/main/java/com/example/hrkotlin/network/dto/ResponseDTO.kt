package com.example.hrkotlin.network.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseDTO<DTO> : Serializable {
    @SerializedName("data")
    var result: DTO? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("errorCode")
    var errorCode: String? = null

    @SerializedName("success")
    var success: Boolean? = false

}