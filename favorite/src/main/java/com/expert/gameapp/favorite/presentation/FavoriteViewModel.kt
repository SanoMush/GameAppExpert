package com.expert.gameapp.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expert.gameapp.core.domain.usecase.GetFavoriteGamesUseCase
import com.expert.gameapp.presentation.mapper.GamePresentationMapper
import com.expert.gameapp.presentation.model.GameUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteGamesUseCase: GetFavoriteGamesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        fetchFavorites()
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            getFavoriteGamesUseCase().collect { games ->
                val uiModels = GamePresentationMapper.mapDomainListToUi(games)
                _uiState.value = FavoriteUiState.Success(uiModels)
            }
        }
    }
}

sealed class FavoriteUiState {
    object Loading : FavoriteUiState()
    data class Success(val games: List<GameUiModel>) : FavoriteUiState()
    data class Error(val message: String) : FavoriteUiState()
}
