package com.expert.gameapp.core.data.mapper

import com.expert.gameapp.core.data.source.local.entity.GameEntity
import com.expert.gameapp.core.data.source.remote.response.GameDetailResponse
import com.expert.gameapp.core.data.source.remote.response.GameItemResponse
import com.expert.gameapp.core.domain.model.Game

object GameDataMapper {

    fun mapResponseToDomain(input: List<GameItemResponse>): List<Game> {
        return input.map {
            Game(
                id = it.id,
                name = it.name,
                backgroundImage = it.backgroundImage ?: "",
                rating = it.rating ?: 0.0,
                released = it.released ?: ""
            )
        }
    }

    fun mapDetailResponseToDomain(input: GameDetailResponse): Game {
        return Game(
            id = input.id,
            name = input.name,
            backgroundImage = input.backgroundImage ?: "",
            rating = input.rating ?: 0.0,
            released = input.released ?: "",
            description = input.description ?: "",
            genres = input.genres?.map { it.name } ?: emptyList(),
            platforms = input.platforms?.map { it.platform.name } ?: emptyList()
        )
    }

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> {
        return input.map {
            Game(
                id = it.id,
                name = it.name,
                backgroundImage = it.backgroundImage,
                rating = it.rating,
                released = it.released,
                isFavorite = true,
                description = it.description,
                genres = it.genres.split(",").filter { genre -> genre.isNotEmpty() },
                platforms = it.platforms.split(",").filter { platform -> platform.isNotEmpty() }
            )
        }
    }

    fun mapDomainToEntity(input: Game): GameEntity {
        return GameEntity(
            id = input.id,
            name = input.name,
            backgroundImage = input.backgroundImage,
            rating = input.rating,
            released = input.released,
            description = input.description,
            genres = input.genres.joinToString(","),
            platforms = input.platforms.joinToString(",")
        )
    }
}
