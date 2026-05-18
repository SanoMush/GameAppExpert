package com.expert.gameapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expert.gameapp.core.domain.usecase.SearchGamesUseCase
import com.expert.gameapp.core.utils.Resource
import com.expert.gameapp.presentation.mapper.GamePresentationMapper
import com.expert.gameapp.presentation.model.GameUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGamesUseCase: SearchGamesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // Debounce
            if (query.isNotEmpty()) {
                searchGames(query)
            } else {
                _uiState.value = SearchUiState.Idle
            }
        }
    }

    private fun searchGames(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            searchGamesUseCase(query).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.value = SearchUiState.Loading
                    is Resource.Success -> {
                        val games = resource.data?.let { GamePresentationMapper.mapDomainListToUi(it) } ?: emptyList()
                        _uiState.value = SearchUiState.Success(games)
                    }
                    is Resource.Error -> _uiState.value = SearchUiState.Error(resource.message ?: "Unknown error")
                }
            }
        }
    }
}

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val games: List<GameUiModel>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
