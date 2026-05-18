package com.expert.gameapp.core.domain.repository

import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getGames(): Flow<Resource<List<Game>>>
    fun getGameDetail(id: Int): Flow<Resource<Game>>
    fun searchGames(query: String): Flow<Resource<List<Game>>>
    
    fun getFavoriteGames(): Flow<List<Game>>
    fun checkFavoriteStatus(id: Int): Flow<Boolean>
    suspend fun setFavoriteGame(game: Game, state: Boolean)
}
