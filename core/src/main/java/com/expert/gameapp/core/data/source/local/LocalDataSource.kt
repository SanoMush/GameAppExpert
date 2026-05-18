package com.expert.gameapp.core.data.source.local

import com.expert.gameapp.core.data.source.local.dao.GameDao
import com.expert.gameapp.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val gameDao: GameDao) {
    fun getFavoriteGames(): Flow<List<GameEntity>> = gameDao.getFavoriteGames()
    
    fun isFavorite(id: Int): Flow<Boolean> = gameDao.isFavorite(id)
    
    suspend fun insertFavoriteGame(game: GameEntity) = gameDao.insertFavoriteGame(game)
    
    suspend fun deleteFavoriteGame(game: GameEntity) = gameDao.deleteFavoriteGame(game)
}
