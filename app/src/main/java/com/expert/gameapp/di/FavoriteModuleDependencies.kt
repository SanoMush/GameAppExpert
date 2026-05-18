package com.expert.gameapp.di

import com.expert.gameapp.core.domain.usecase.GetFavoriteGamesUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun getFavoriteGamesUseCase(): GetFavoriteGamesUseCase
}
