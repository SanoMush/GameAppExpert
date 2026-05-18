package com.expert.gameapp.favorite.di

import com.expert.gameapp.di.FavoriteModuleDependencies
import com.expert.gameapp.favorite.presentation.FavoriteActivity
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun dependencies(deps: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}
