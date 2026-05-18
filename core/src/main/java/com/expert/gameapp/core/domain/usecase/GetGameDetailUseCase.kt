package com.expert.gameapp.core.domain.usecase

import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.domain.repository.IGameRepository
import com.expert.gameapp.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameDetailUseCase @Inject constructor(
    private val repository: IGameRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Game>> = repository.getGameDetail(id)
}
