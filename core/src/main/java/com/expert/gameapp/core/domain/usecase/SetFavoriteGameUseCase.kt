package com.expert.gameapp.core.domain.usecase

import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.domain.repository.IGameRepository
import javax.inject.Inject

class SetFavoriteGameUseCase @Inject constructor(
    private val repository: IGameRepository
) {
    suspend operator fun invoke(game: Game, state: Boolean) {
        repository.setFavoriteGame(game, state)
    }
}
