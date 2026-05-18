package com.expert.gameapp.presentation.mapper

import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.presentation.model.GameUiModel

object GamePresentationMapper {
    fun mapDomainToUi(input: Game): GameUiModel {
        return GameUiModel(
            id = input.id,
            name = input.name,
            backgroundImage = input.backgroundImage,
            rating = input.rating.toString(),
            released = input.released.ifEmpty { "TBA" },
            isFavorite = input.isFavorite,
            description = input.description,
            genres = input.genres.joinToString(", "),
            platforms = input.platforms.joinToString(", ")
        )
    }

    fun mapDomainListToUi(input: List<Game>): List<GameUiModel> {
        return input.map { mapDomainToUi(it) }
    }
}
