package com.example.houseplants.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.houseplants.R
import com.example.houseplants.model.Plant
import com.example.houseplants.model.PlantDetail
import com.example.houseplants.ui.navigation.NavigationDestination

object SavedPlantsDestination : NavigationDestination {
    override val route = "saved_plants"
    override val titleRes = R.string.saved_plants
}
@Composable
fun SavedScreen(windowSize: WindowHeightSizeClass,
                onCardClickAction: (Int) ->Unit, modifier: Modifier = Modifier, savedPlants: List<PlantDetail>){
    when(windowSize) {
        WindowHeightSizeClass.Compact -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.padding(8.dp)
            ) {
                items(savedPlants) { plant ->
                    GridSavedPlantCard(
                        onClickAction = { onCardClickAction(plant.id) },
                        plant = plant
                    )
                }
            }
        }

        else -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.padding(8.dp)
            ) {
                items(savedPlants) { plant ->
                    SavedPlantCard(onClickAction = { onCardClickAction(plant.id) }, plant = plant)
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedPlantCard(onClickAction: () -> Unit,plant: PlantDetail, modifier:Modifier = Modifier) {
    Card(
        onClick = onClickAction,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(plant.defaultImage?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = plant.commonName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridSavedPlantCard(onClickAction: () -> Unit, plant: PlantDetail, modifier: Modifier = Modifier) {
    Card(
        onClick = onClickAction,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(plant.defaultImage?.regularUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))


            )
            Text(
                text = plant.commonName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(80.dp)
            )
        }
    }
}