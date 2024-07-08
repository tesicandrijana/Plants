package com.example.houseplants.ui
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.houseplants.PlantsApplication
import com.example.houseplants.data.PlantsRepository
import com.example.houseplants.model.Plant
import com.example.houseplants.model.PlantDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PlantsViewModel(private val plantsRepository: PlantsRepository) : ViewModel() {

    val savedPlantsUiState: StateFlow<SavedPlantsUiState> = plantsRepository.getAllPlantsStream().map { SavedPlantsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SavedPlantsUiState()
        )
    var plantsUiState: PlantsUiState by mutableStateOf(PlantsUiState.Loading)
        private set

    var group by mutableStateOf("Poisonous")
        private set

    var filter by mutableStateOf("None")
        private set

    init {
        getPlants()
    }

    fun setSelectedGroup(selectedGroup: String) {
        group = selectedGroup
        getPlants()
    }

    fun setSelectedFilter(selectedFilter: String) {
        filter = selectedFilter
        getPlants()
    }

    fun getPlants() {
        viewModelScope.launch {
            try {
                val listResult = when (group) {
                    "Poisonous" -> plantsRepository.getPoisonousPlants()
                    "Edible" -> plantsRepository.getEdiblePlants()
                    "Indoor" -> plantsRepository.getIndoorsPlants()
                    else -> plantsRepository.getOutdoorsPlants()
                }

                plantsUiState = PlantsUiState.Success(chosenGroup = group, chosenFilter = filter, plants = if (filter != "None") listResult.data.filter { it.watering == filter } else listResult.data)
            } catch (e: IOException) {
                plantsUiState = PlantsUiState.Error
                Log.e("PlantsViewModel", "IOException occurred: ", e)
            } catch (e: HttpException) {
                plantsUiState = PlantsUiState.Error
                Log.e("PlantsViewModel", "HttpExceptions: ", e)
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L


    }
}

object AppViewModelProvider{
    val Factory: ViewModelProvider.Factory = viewModelFactory {

        initializer {
            PlantsViewModel(plantsRepository = plantsApplication().container.plantsRepository)
        }
        initializer {
            PlantDetailViewModel(
                this.createSavedStateHandle(),
                plantsRepository = plantsApplication().container.plantsRepository
            )
        }
    }

}

fun CreationExtras.plantsApplication(): PlantsApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PlantsApplication)


