package com.expert.gameapp.core.data.source.remote.api

import com.expert.gameapp.core.data.source.remote.response.GameDetailResponse
import com.expert.gameapp.core.data.source.remote.response.GameListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") apiKey: String,
        @Query("page_size") pageSize: Int = 20
    ): GameListResponse

    @GET("games")
    suspend fun searchGames(
        @Query("key") apiKey: String,
        @Query("search") query: String,
        @Query("page_size") pageSize: Int = 20
    ): GameListResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): GameDetailResponse
}
