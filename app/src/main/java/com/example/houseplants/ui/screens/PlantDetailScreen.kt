package com.example.houseplants.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.houseplants.R
import com.example.houseplants.data.Converters.fromStringList
import com.example.houseplants.model.PlantDetail
import com.example.houseplants.ui.AppViewModelProvider
import com.example.houseplants.ui.PlantDetailViewModel
import com.example.houseplants.ui.navigation.NavigationDestination


object PlantDetailsDestination : NavigationDestination {
    override val route = "plant_details"
    override val titleRes = R.string.plant_details
    const val plantDetailIdArg = "plantDetailsId"
    val routeWithArgs = "$route/{$plantDetailIdArg}"
}

@Composable
fun PlantDetailScreen(
    windowSize: WindowHeightSizeClass,
    plantDetailViewModel: PlantDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val plantDetailUiState = plantDetailViewModel.plantDetailUiState
    val plant = plantDetailUiState.plantDetail

    Box(modifier = Modifier.fillMaxSize()) {
        when(windowSize) {
            WindowHeightSizeClass.Compact ->{
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(plant?.defaultImage?.regularUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .height(250.dp)
                    )
                    Information(plant = plant)
                }
            }
            else -> {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(plant?.defaultImage?.regularUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Information(plant = plant)
                }
            }}


        FloatingActionButton(
            onClick = {
                if (plantDetailUiState.saved) {
                    plantDetailViewModel.removeFromSaved()
                } else {
                    plantDetailViewModel.savePlant()
                }
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (plantDetailUiState.saved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (plantDetailUiState.saved) "Unsave" else "Save"
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun Information( plant: PlantDetail?){
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = plant?.commonName ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )
        }

        plant?.scientificName?.let { names ->
            item {
                SectionHeader(title = "Scientific Names:")
            }
            items(names) { name ->
                Text(text = " $name")
            }
        }

        plant?.otherName?.takeIf { it.isNotEmpty() }?.let { names ->
            item {
                SectionHeader(title = "Other Names:")
                Text(fromStringList(names))
            }
        }

        plant?.description?.let {
            item {
                SectionHeader(title = "Description:")
                Text(text = it)
            }
        }

        plant?.family?.let {
            item {
                SectionHeader(title = "Family:")
                Text(text = it)
            }
        }

        plant?.origin?.let { origins ->
            item {
                SectionHeader(title = "Origin:")
                Text(fromStringList(origins))
            }
        }

        plant?.type?.let {
            item {
                SectionHeader(title = "Type:")
                Text(text = it)
            }
        }

        plant?.dimension?.let {
            item {
                SectionHeader(title = "Dimension:")
                Text(text = it)
            }
        }

        plant?.cycle?.let {
            item {
                SectionHeader(title = "Cycle:")
                Text(text = it)
            }
        }

        plant?.watering?.let {
            item {
                SectionHeader(title = "Watering:")
                Text(text = it)
            }
        }

        plant?.wateringGeneralBenchmark?.value?.let { value ->
            item {
                SectionHeader(title = "Watering General Benchmark:")
                Text(text = " $value ${plant.wateringGeneralBenchmark.unit}")
            }
        }

        plant?.plantAnatomy?.let { anatomy ->
            item {
                SectionHeader(title = "Plant Anatomy:")
            }
            items(anatomy) { part ->
                part.color.forEach { color ->
                    Text(text = "${part.part}: $color")
                }
            }
        }

        plant?.sunlight?.let { sunlight ->
            item {
                SectionHeader(title = "Sunlight:")
                Text(fromStringList(sunlight))
            }
        }

        plant?.pruningMonth?.let { months ->
            item {
                SectionHeader(title = "Pruning Months:")
                Text(fromStringList(months))
            }
        }

        plant?.seeds?.let {
            item {
                SectionHeader(title = "Seeds")
                Text(text = it.toString())
            }
        }

        if(plant?.attracts?.isNotEmpty() == true){
        plant.attracts.let { attracts ->
            item {
                SectionHeader(title = "Attracts:")
                Text(fromStringList(attracts))
            }
        }}

        plant?.propagation?.let { propagation ->
            item {
                SectionHeader(title = "Propagation:")
                Text(fromStringList(propagation))
            }
        }

        plant?.hardiness?.let { hardiness ->
            item {
                SectionHeader(title = "Hardiness:")
            }
            item {
                Text(text = "Min: ${hardiness.min}")
                Text(text = "Max: ${hardiness.max}")
            }
        }

        plant?.flowers?.let {
            item {
                SectionHeader(title = "Flowers:")
                Text(if (it) "Yes" else "No")
            }
        }

        if (plant?.flowers != false) {
            plant?.floweringSeason?.let {
                item {
                    SectionHeader(title = "Flowering Season:")
                    Text(text = it)
                }
            }

            plant?.flowerColor?.let {
                item {
                    SectionHeader(title = "Flower Color:")
                    Text(text = it)
                }
            }
        }

        if(plant?.soil?.isNotEmpty() == true)
        plant.soil.let { soil ->
            item {
                SectionHeader(title = "Soil:")
                Text(fromStringList(soil))
            }
        }

        if(plant?.pestSusceptibility?.isNotEmpty() == true)
        plant.pestSusceptibility.let { pests ->
            item {
                SectionHeader(title = "Pest Susceptibility:")
                Text(fromStringList(pests))
            }
        }

        plant?.cones?.let {
            item {
                SectionHeader(title = "Cones:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.fruits?.let {
            item {
                SectionHeader(title = "Fruits:")
                Text(if (it) "Yes" else "No")
            }
        }

        if (plant?.fruits != false) {
            plant?.edibleFruit?.let {
                item {
                    SectionHeader(title = "Edible Fruit:")
                    Text(if (it) "Yes" else "No")
                }
            }

            plant?.fruitColor?.let { colors ->
                item {
                    SectionHeader(title = "Fruit Color:")
                    Text(fromStringList(colors))
                }
            }

            plant?.fruitingSeason?.let {
                item {
                    SectionHeader(title = "Fruiting Season:")
                    Text(text = it)
                }
            }

            plant?.harvestSeason?.let {
                item {
                    SectionHeader(title = "Harvest Season:")
                    Text(text = it)
                }
            }

            plant?.harvestMethod?.let {
                item {
                    SectionHeader(title = "Harvest Method:")
                    Text(text = it)
                }
            }
        }

        plant?.leaf?.let {
            item {
                SectionHeader(title = "Leaf:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.leafColor?.let { colors ->
            item {
                SectionHeader(title = "Leaf Color:")
                Text(fromStringList(colors))
            }
        }

        plant?.edibleLeaf?.let {
            item {
                SectionHeader(title = "Edible Leaf:")
                Text(if (it) "Yes" else "False")
            }
        }

        plant?.growthRate?.let {
            item {
                SectionHeader(title = "Growth Rate:")
                Text(text = it)
            }
        }

        plant?.maintenance?.let {
            item {
                SectionHeader(title = "Maintenance:")
                Text(text = it)
            }
        }

        plant?.medicinal?.let {
            item {
                SectionHeader(title = "Medicinal:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.poisonousToHumans?.let {
            item {
                SectionHeader(title = "Poisonous to Humans:")
                Text(if (it == 1) "Yes" else "No")
            }
        }

        plant?.poisonousToPets?.let {
            item {
                SectionHeader(title = "Poisonous to Pets:")
                Text(if (it == 1) "Yes" else "No")
            }
        }

        plant?.droughtTolerant?.let {
            item {
                SectionHeader(title = "Drought Tolerant:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.saltTolerant?.let {
            item {
                SectionHeader(title = "Salt Tolerant:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.thorny?.let {
            item {
                SectionHeader(title = "Thorny:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.invasive?.let {
            item {
                SectionHeader(title = "Invasive:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.rare?.let {
            item {
                SectionHeader(title = "Rare:")
                Text(text = if (it) "Yes" else "No")
            }
        }

        plant?.rareLevel?.let {
            item {
                SectionHeader(title = "Rare Level:")
                Text(text = it)
            }
        }

        plant?.tropical?.let {
            item {
                SectionHeader(title = "Tropical:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.cuisine?.let {
            item {
                SectionHeader(title = "Cuisine:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.indoor?.let {
            item {
                SectionHeader(title = "Indoor:")
                Text(if (it) "Yes" else "No")
            }
        }

        plant?.careLevel?.let {
            item {
                SectionHeader(title = "Care Level:")
                Text(text = it)
            }
        }
    }
}
