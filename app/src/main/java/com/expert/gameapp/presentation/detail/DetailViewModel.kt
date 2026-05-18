package com.expert.gameapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.domain.usecase.CheckFavoriteStatusUseCase
import com.expert.gameapp.core.domain.usecase.GetGameDetailUseCase
import com.expert.gameapp.core.domain.usecase.SetFavoriteGameUseCase
import com.expert.gameapp.core.utils.Resource
import com.expert.gameapp.presentation.mapper.GamePresentationMapper
import com.expert.gameapp.presentation.model.GameUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getGameDetailUseCase: GetGameDetailUseCase,
    private val setFavoriteGameUseCase: SetFavoriteGameUseCase,
    private val checkFavoriteStatusUseCase: CheckFavoriteStatusUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var currentGame: Game? = null

    init {
        val gameId = savedStateHandle.get<String>("gameId")?.toIntOrNull()
        if (gameId != null) {
            fetchGameDetail(gameId)
            checkFavoriteStatus(gameId)
        } else {
            _uiState.value = DetailUiState.Error("Invalid Game ID")
        }
    }

    private fun fetchGameDetail(id: Int) {
        viewModelScope.launch {
            getGameDetailUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.value = DetailUiState.Loading
                    is Resource.Success -> {
                        resource.data?.let {
                            currentGame = it
                            _uiState.value = DetailUiState.Success(GamePresentationMapper.mapDomainToUi(it))
                        }
                    }
                    is Resource.Error -> _uiState.value = DetailUiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }

    private fun checkFavoriteStatus(id: Int) {
        viewModelScope.launch {
            checkFavoriteStatusUseCase(id).collect { isFav ->
                _isFavorite.value = isFav
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            currentGame?.let { game ->
                val newState = !_isFavorite.value
                setFavoriteGameUseCase(game, newState)
            }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val game: GameUiModel) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}
