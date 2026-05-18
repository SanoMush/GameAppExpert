package com.expert.gameapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<GameItemResponse>
)
