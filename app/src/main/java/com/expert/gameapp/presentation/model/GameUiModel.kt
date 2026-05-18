package com.expert.gameapp.presentation.model

data class GameUiModel(
    val id: Int,
    val name: String,
    val backgroundImage: String,
    val rating: String,
    val released: String,
    val isFavorite: Boolean = false,
    val description: String = "",
    val genres: String = "",
    val platforms: String = ""
)
