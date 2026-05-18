package com.expert.gameapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("released")
    val released: String?
)
