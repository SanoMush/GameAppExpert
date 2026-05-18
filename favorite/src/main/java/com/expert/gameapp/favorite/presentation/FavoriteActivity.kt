package com.expert.gameapp.favorite.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.expert.gameapp.presentation.component.ErrorState
import com.expert.gameapp.presentation.component.GameCard
import com.expert.gameapp.presentation.component.LoadingIndicator
import com.expert.gameapp.presentation.theme.GameAppExpertTheme
import com.expert.gameapp.di.FavoriteModuleDependencies
import com.expert.gameapp.favorite.di.DaggerFavoriteComponent
import com.expert.gameapp.favorite.di.FavoriteViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : ComponentActivity() {

    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Manual DI using Hilt EntryPoint
        val dependencies = EntryPointAccessors.fromApplication(
            applicationContext,
            FavoriteModuleDependencies::class.java
        )

        DaggerFavoriteComponent.builder()
            .dependencies(dependencies)
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        
        setContent {
            GameAppExpertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FavoriteScreen(
                        viewModel = viewModel,
                        navigateBack = { finish() },
                        navigateToDetail = { gameId ->
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("gameapp://detail/$gameId")
                            ).apply {
                                putExtra("from_favorite", true)
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Games") },
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
            when (uiState) {
                is FavoriteUiState.Loading -> LoadingIndicator()
                is FavoriteUiState.Success -> {
                    val games = (uiState as FavoriteUiState.Success).games
                    if (games.isEmpty()) {
                        ErrorState(message = "No favorite games yet")
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(games, key = { it.id }) { game ->
                                GameCard(
                                    game = game,
                                    onClick = { navigateToDetail(game.id) }
                                )
                            }
                        }
                    }
                }
                is FavoriteUiState.Error -> {
                    val message = (uiState as FavoriteUiState.Error).message
                    ErrorState(message = message)
                }
            }
        }
    }
}
