package com.example.houseplants.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.houseplants.R
import com.example.houseplants.model.Plant
import com.example.houseplants.ui.AppViewModelProvider
import com.example.houseplants.ui.PlantsViewModel
import com.example.houseplants.ui.navigation.NavigationDestination

object PlantGalleryDestination : NavigationDestination {
    override val route = "plant_gallery"
    override val titleRes = R.string.plant_gallery
}
@Composable
fun PlantGalleryScreen(
    windowSize: WindowHeightSizeClass,
    plantsViewModel: PlantsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onCardClickAction: (Int) -> Unit,
    selectedGroup: String,
    filter: String,
    plants: List<Plant>,
    modifier:Modifier = Modifier) {

    Column(modifier = modifier) {
        Row {
            ChooseGroupDropdown(
                selected = selectedGroup,
                selectGroupAction = { selectedGroup ->
                    plantsViewModel.setSelectedGroup(selectedGroup)
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FilterByCycle(
                filter = filter,
                setFilter = { selectedFilter -> plantsViewModel.setSelectedFilter(selectedFilter) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        if(plants.isEmpty()) Text("No available results")
        when(windowSize) {
            WindowHeightSizeClass.Compact ->{
                PlantGrid(onCardClickAction = { plant -> onCardClickAction(plant.id)}, plants = plants)
            }
            else -> { PlantList(onCardClickAction = { plant -> onCardClickAction(plant.id)}, plants = plants) }
        }
    }
}

@Composable
fun ChooseGroupDropdown(selected: String, selectGroupAction: (String) -> Unit,modifier: Modifier = Modifier){
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        TextButton(onClick = { expanded = true }) {
            Text(selected)
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("Poisonous") }, onClick = { selectGroupAction("Poisonous") })
                DropdownMenuItem(text = { Text("Edible") }, onClick = { selectGroupAction("Edible") })
                DropdownMenuItem(text = { Text("Indoors") }, onClick = { selectGroupAction("Indoor")})
                DropdownMenuItem(text = { Text("Outdoors") }, onClick = { selectGroupAction("Outdoor")})
            }
        }
    }
}

@Composable
fun FilterByCycle(filter: String,setFilter: (String) -> Unit,modifier: Modifier = Modifier){
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier){
        TextButton(onClick = { expanded = true }) {
            Text(filter)
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false}) {
                DropdownMenuItem(text = { Text("None")}, onClick = { setFilter("None") })
                DropdownMenuItem(text = { Text("Minimum watering")}, onClick = { setFilter("Minimum") })
                DropdownMenuItem(text = { Text("Average watering")}, onClick = { setFilter("Average") })
                DropdownMenuItem(text = { Text("Frequent watering")}, onClick = { setFilter("Frequent") })
            }
        }
    }
}