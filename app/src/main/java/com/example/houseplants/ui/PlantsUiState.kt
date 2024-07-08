package com.example.houseplants.ui

import com.example.houseplants.model.Plant
import com.example.houseplants.model.PlantDetail
import com.example.houseplants.model.PlantListResponse

sealed interface PlantsUiState {
    data class Success(
        val chosenGroup: String,
        val chosenFilter: String,
        //val selectedPlant: Plant? = Plant(0,"",null, null, "", "", null, null),
        val selectedPlantId: Int? = null,
        //val plantDetail: PlantDetail? = null,
        val plants: List<Plant>,
    ): PlantsUiState

    object Error: PlantsUiState
    object Loading: PlantsUiState
}