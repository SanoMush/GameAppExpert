package com.expert.gameapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.expert.gameapp.presentation.component.ErrorState
import com.expert.gameapp.presentation.component.LoadingIndicator
import com.expert.gameapp.presentation.component.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState is DetailUiState.Success) {
                FloatingActionButton(onClick = { viewModel.toggleFavorite() }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle Favorite"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is DetailUiState.Loading -> LoadingIndicator()
                is DetailUiState.Success -> {
                    val game = (uiState as DetailUiState.Success).game
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        AsyncImage(
                            model = game.backgroundImage,
                            contentDescription = game.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = game.name,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            RatingBar(rating = game.rating)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Released: ${game.released}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Genres: ${game.genres}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Platforms: ${game.platforms}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = game.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                is DetailUiState.Error -> {
                    val message = (uiState as DetailUiState.Error).message
                    ErrorState(message = message)
                }
            }
        }
    }
}
