package com.example.houseplants.ui

import com.example.houseplants.model.PlantDetail

data class SavedPlantsUiState (
    val plantList: List<PlantDetail> = listOf()
)