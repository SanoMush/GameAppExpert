package com.expert.gameapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.expert.gameapp.presentation.component.ErrorState
import com.expert.gameapp.presentation.component.GameCard
import com.expert.gameapp.presentation.component.LoadingIndicator
import com.expert.gameapp.presentation.component.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it) }
            )

            when (uiState) {
                is SearchUiState.Idle -> {
                    // Show nothing or initial state
                }
                is SearchUiState.Loading -> LoadingIndicator()
                is SearchUiState.Success -> {
                    val games = (uiState as SearchUiState.Success).games
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(games, key = { it.id }) { game ->
                            GameCard(
                                game = game,
                                onClick = { navigateToDetail(game.id) }
                            )
                        }
                    }
                }
                is SearchUiState.Error -> {
                    val message = (uiState as SearchUiState.Error).message
                    ErrorState(message = message)
                }
            }
        }
    }
}
