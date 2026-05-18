package com.expert.gameapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expert.gameapp.core.domain.usecase.GetGamesUseCase
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
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            getGamesUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.value = HomeUiState.Loading
                    is Resource.Success -> {
                        val games = resource.data?.let { GamePresentationMapper.mapDomainListToUi(it) } ?: emptyList()
                        _uiState.value = HomeUiState.Success(games)
                    }
                    is Resource.Error -> _uiState.value = HomeUiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val games: List<GameUiModel>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
