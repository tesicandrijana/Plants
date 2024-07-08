package com.example.houseplants.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houseplants.data.PlantsRepository
import com.example.houseplants.model.PlantDetail
import com.example.houseplants.ui.screens.PlantDetailsDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PlantDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val plantsRepository: PlantsRepository
) : ViewModel() {
    var plantDetailUiState by mutableStateOf(PlantDetailUiState())
        private set

    private val plantId: Int = checkNotNull(savedStateHandle[PlantDetailsDestination.plantDetailIdArg])

    init {
        viewModelScope.launch {
            try {
                val plantSaved = isPlantSaved()
                val plantDetail = if (plantSaved) {
                    plantsRepository.getPlantStream(plantId)
                        .filterNotNull()
                        .first()
                } else {
                    plantsRepository.getPlantDetail(plantId)
                }
                plantDetailUiState = PlantDetailUiState(saved = plantSaved, plantDetail = plantDetail)
            } catch (e: IOException) {
                Log.e("PlantDetailViewModel", "IOException occurred: ", e)
                plantDetailUiState = PlantDetailUiState(error = true)
            } catch (e: HttpException) {
                Log.e("PlantDetailViewModel", "HttpExceptions: ", e)
                plantDetailUiState = PlantDetailUiState(error = true)
            }
        }
    }

    fun savePlant() {
        viewModelScope.launch {
            plantDetailUiState.plantDetail?.let {
                plantsRepository.insertPlant(it)
                plantDetailUiState = plantDetailUiState.copy(saved = true)
            } ?: Log.e("PlantDetailViewModel", "No plant detail to save")
        }
    }

    fun removeFromSaved() {
        viewModelScope.launch {
            plantDetailUiState.plantDetail?.let {
                plantsRepository.deletePlant(it)
                plantDetailUiState = plantDetailUiState.copy(saved = false)
            }
        }
    }

    suspend fun isPlantSaved(): Boolean {
        return plantsRepository.getPlantStream(plantId).firstOrNull() != null
    }

}

data class PlantDetailUiState(
    val saved: Boolean = false,
    val plantDetail: PlantDetail? = null,
    val error: Boolean = false
)

