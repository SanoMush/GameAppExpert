package com.expert.gameapp.core.di

import com.expert.gameapp.core.data.repository.GameRepositoryImpl
import com.expert.gameapp.core.domain.repository.IGameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGameRepository(gameRepositoryImpl: GameRepositoryImpl): IGameRepository
}
