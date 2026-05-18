package com.expert.gameapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.expert.gameapp.R
import com.expert.gameapp.presentation.component.ErrorState
import com.expert.gameapp.presentation.component.GameCard
import com.expert.gameapp.presentation.component.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDetail: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToFavorite: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.games_title)) },
                actions = {
                    IconButton(onClick = navigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_content_description))
                    }
                    IconButton(onClick = navigateToFavorite) {
                        Icon(Icons.Default.Favorite, contentDescription = stringResource(R.string.favorite_content_description))
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
            when (uiState) {
                is HomeUiState.Loading -> LoadingIndicator()
                is HomeUiState.Success -> {
                    val games = (uiState as HomeUiState.Success).games
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(games, key = { it.id }) { game ->
                            GameCard(
                                game = game,
                                onClick = { navigateToDetail(game.id) }
                            )
                        }
                    }
                }
                is HomeUiState.Error -> {
                    val message = (uiState as HomeUiState.Error).message
                    ErrorState(message = message)
                }
            }
        }
    }
}
