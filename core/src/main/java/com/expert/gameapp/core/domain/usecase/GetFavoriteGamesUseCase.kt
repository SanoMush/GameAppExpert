package com.expert.gameapp.core.domain.usecase

import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val repository: IGameRepository
) {
    operator fun invoke(): Flow<List<Game>> = repository.getFavoriteGames()
}
