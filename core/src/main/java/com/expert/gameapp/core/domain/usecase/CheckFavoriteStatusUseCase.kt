package com.expert.gameapp.core.domain.usecase

import com.expert.gameapp.core.domain.repository.IGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckFavoriteStatusUseCase @Inject constructor(
    private val repository: IGameRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> = repository.checkFavoriteStatus(id)
}
