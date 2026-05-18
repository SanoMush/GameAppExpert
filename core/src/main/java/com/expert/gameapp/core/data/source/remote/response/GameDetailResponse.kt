package com.expert.gameapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description_raw")
    val description: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("released")
    val released: String?,
    @SerializedName("genres")
    val genres: List<GenreResponse>?,
    @SerializedName("platforms")
    val platforms: List<PlatformWrapperResponse>?
)

data class GenreResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class PlatformWrapperResponse(
    @SerializedName("platform")
    val platform: PlatformResponse
)

data class PlatformResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
