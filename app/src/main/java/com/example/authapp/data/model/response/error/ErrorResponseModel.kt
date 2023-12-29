package com.example.authapp.data.model.response.error

import com.google.gson.annotations.SerializedName

data class ErrorResponseModel(
    @SerializedName("error") val message: String
)
