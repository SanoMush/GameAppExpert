package com.expert.gameapp.core.data.repository

import com.expert.gameapp.core.data.mapper.GameDataMapper
import com.expert.gameapp.core.data.source.local.LocalDataSource
import com.expert.gameapp.core.data.source.remote.ApiResponse
import com.expert.gameapp.core.data.source.remote.RemoteDataSource
import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.domain.repository.IGameRepository
import com.expert.gameapp.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGameRepository {

    override fun getGames(): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getGames().first()) {
            is ApiResponse.Success -> {
                val games = GameDataMapper.mapResponseToDomain(apiResponse.data)
                emit(Resource.Success(games))
            }
            is ApiResponse.Empty -> emit(Resource.Success(emptyList()))
            is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
        }
    }

    override fun getGameDetail(id: Int): Flow<Resource<Game>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.getGameDetail(id).first()) {
            is ApiResponse.Success -> {
                val game = GameDataMapper.mapDetailResponseToDomain(apiResponse.data)
                // Check local if it's favorite
                val isFav = localDataSource.isFavorite(id).first()
                emit(Resource.Success(game.copy(isFavorite = isFav)))
            }
            is ApiResponse.Empty -> emit(Resource.Error("Game not found"))
            is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
        }
    }

    override fun searchGames(query: String): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchGames(query).first()) {
            is ApiResponse.Success -> {
                val games = GameDataMapper.mapResponseToDomain(apiResponse.data)
                emit(Resource.Success(games))
            }
            is ApiResponse.Empty -> emit(Resource.Success(emptyList()))
            is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
        }
    }

    override fun getFavoriteGames(): Flow<List<Game>> {
        return localDataSource.getFavoriteGames().map { 
            GameDataMapper.mapEntitiesToDomain(it) 
        }
    }

    override fun checkFavoriteStatus(id: Int): Flow<Boolean> {
        return localDataSource.isFavorite(id)
    }

    override suspend fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = GameDataMapper.mapDomainToEntity(game)
        if (state) {
            localDataSource.insertFavoriteGame(gameEntity)
        } else {
            localDataSource.deleteFavoriteGame(gameEntity)
        }
    }
}
