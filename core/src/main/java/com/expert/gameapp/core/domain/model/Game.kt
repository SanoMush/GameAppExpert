package com.expert.gameapp.core.domain.model

data class Game(
    val id: Int,
    val name: String,
    val backgroundImage: String,
    val rating: Double,
    val released: String,
    val isFavorite: Boolean = false,
    val description: String = "",
    val genres: List<String> = emptyList(),
    val platforms: List<String> = emptyList()
)
