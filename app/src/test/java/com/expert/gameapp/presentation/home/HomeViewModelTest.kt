package com.expert.gameapp.presentation.home

import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.domain.usecase.GetGamesUseCase
import com.expert.gameapp.core.utils.Resource
import com.expert.gameapp.presentation.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getGamesUseCase: GetGamesUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Test
    fun `when fetchGames should update uiState to Success when use case returns Success`() = runTest {
        // Arrange
        val mockGames = listOf(
            Game(
                id = 1,
                name = "Game 1",
                backgroundImage = "http://image.com",
                rating = 4.5,
                released = "2023-01-01",
                isFavorite = false,
                description = "Description"
            )
        )
        val expectedResource = Resource.Success(mockGames)
        `when`(getGamesUseCase()).thenReturn(flowOf(expectedResource))

        // Act
        homeViewModel = HomeViewModel(getGamesUseCase)

        // Assert
        val currentState = homeViewModel.uiState.value
        assertTrue(currentState is HomeUiState.Success)
        val games = (currentState as HomeUiState.Success).games
        assertEquals(1, games.size)
        assertEquals("Game 1", games[0].name)
    }

    @Test
    fun `when fetchGames should update uiState to Error when use case returns Error`() = runTest {
        // Arrange
        val expectedResource = Resource.Error<List<Game>>("Network Error")
        `when`(getGamesUseCase()).thenReturn(flowOf(expectedResource))

        // Act
        homeViewModel = HomeViewModel(getGamesUseCase)

        // Assert
        val currentState = homeViewModel.uiState.value
        assertTrue(currentState is HomeUiState.Error)
        assertEquals("Network Error", (currentState as HomeUiState.Error).message)
    }

    @Test
    fun `when fetchGames should update uiState to Loading when use case returns Loading`() = runTest {
        // Arrange
        val expectedResource = Resource.Loading<List<Game>>()
        `when`(getGamesUseCase()).thenReturn(flowOf(expectedResource))

        // Act
        homeViewModel = HomeViewModel(getGamesUseCase)

        // Assert
        val currentState = homeViewModel.uiState.value
        assertTrue(currentState is HomeUiState.Loading)
    }
}
