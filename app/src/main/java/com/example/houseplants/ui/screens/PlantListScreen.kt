package com.example.houseplants.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.houseplants.model.Plant
import com.example.houseplants.model.PlantDetail

@Composable
fun PlantList(onCardClickAction: (Plant) -> Unit, plants: List<Plant>, modifier: Modifier = Modifier) {
    val plantsWithImage = plants.filter { it.defaultImage != null && it.defaultImage.thumbnail != null }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(8.dp)
        ) {
            items(plantsWithImage) { plant ->
                PlantCard(onClickAction = { onCardClickAction(plant) }, plant = plant)
            }
        }
}

@Composable
fun PlantGrid(onCardClickAction: (Plant) -> Unit, plants: List<Plant>, modifier: Modifier = Modifier) {
    val plantsWithImage = plants.filter { it.defaultImage != null && it.defaultImage.thumbnail != null }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items(plantsWithImage) { plant ->
            GridPlantCard(onClickAction = { onCardClickAction(plant) }, plant = plant)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridPlantCard(onClickAction: () -> Unit, plant: Plant, modifier: Modifier = Modifier){
    Card(
        onClick = onClickAction,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCard(onClickAction: () -> Unit, plant: Plant, modifier: Modifier = Modifier) {
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

