package com.expert.gameapp.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_games")
data class GameEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val backgroundImage: String,
    val rating: Double,
    val released: String,
    val description: String,
    val genres: String,
    val platforms: String
)
