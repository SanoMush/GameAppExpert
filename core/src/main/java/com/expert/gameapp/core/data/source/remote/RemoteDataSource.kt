package com.expert.gameapp.core.data.source.remote

import com.expert.gameapp.core.BuildConfig
import com.expert.gameapp.core.data.source.remote.api.ApiService
import com.expert.gameapp.core.data.source.remote.response.GameDetailResponse
import com.expert.gameapp.core.data.source.remote.response.GameItemResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getGames(): Flow<ApiResponse<List<GameItemResponse>>> {
        return flow {
            try {
                val response = apiService.getGames(apiKey = BuildConfig.RAWG_API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchGames(query: String): Flow<ApiResponse<List<GameItemResponse>>> {
        return flow {
            try {
                val response = apiService.searchGames(apiKey = BuildConfig.RAWG_API_KEY, query = query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getGameDetail(id: Int): Flow<ApiResponse<GameDetailResponse>> {
        return flow {
            try {
                val response = apiService.getGameDetail(id = id, apiKey = BuildConfig.RAWG_API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}
