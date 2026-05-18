package com.expert.gameapp.core.domain.usecase

import com.expert.gameapp.core.domain.model.Game
import com.expert.gameapp.core.domain.repository.IGameRepository
import com.expert.gameapp.core.utils.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetGamesUseCaseTest {

    @Mock
    private lateinit var gameRepository: IGameRepository

    private lateinit var getGamesUseCase: GetGamesUseCase

    @Before
    fun setUp() {
        getGamesUseCase = GetGamesUseCase(gameRepository)
    }

    @Test
    fun `when invoke getGames should return Success with list of games`() = runTest {
        // Arrange
        val mockGames = listOf(
            Game(1, "Game 1", "2023", "http://image.com", 4.5, "Description", false)
        )
        val expectedResource = Resource.Success(mockGames)
        `when`(gameRepository.getGames()).thenReturn(flowOf(expectedResource))

        // Act
        val result = getGamesUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Success)
        assertEquals(mockGames, result[0].data)
    }

    @Test
    fun `when invoke getGames should return Error when data is failed to load`() = runTest {
        // Arrange
        val expectedResource = Resource.Error<List<Game>>("Network Error")
        `when`(gameRepository.getGames()).thenReturn(flowOf(expectedResource))

        // Act
        val result = getGamesUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals("Network Error", result[0].message)
    }
}
