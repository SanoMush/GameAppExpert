package com.expert.gameapp.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.expert.gameapp.core.domain.usecase.GetFavoriteGamesUseCase
import com.expert.gameapp.favorite.presentation.FavoriteViewModel
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    private val getFavoriteGamesUseCase: GetFavoriteGamesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(getFavoriteGamesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
