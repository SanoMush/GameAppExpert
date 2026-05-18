package com.expert.gameapp.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.expert.gameapp.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM favorite_games")
    fun getFavoriteGames(): Flow<List<GameEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorite_games WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGame(game: GameEntity)

    @Delete
    suspend fun deleteFavoriteGame(game: GameEntity)
}
