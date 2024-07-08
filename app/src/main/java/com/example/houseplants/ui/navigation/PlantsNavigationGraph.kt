package com.example.houseplants.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.houseplants.ui.AppViewModelProvider
import com.example.houseplants.ui.PlantsApp
import com.example.houseplants.ui.PlantsAppBar
import com.example.houseplants.ui.PlantsUiState
import com.example.houseplants.ui.PlantsViewModel
import com.example.houseplants.ui.screens.HomeDestination
import com.example.houseplants.ui.screens.HomeScreen
import com.example.houseplants.ui.screens.PlantDetailScreen
import com.example.houseplants.ui.screens.PlantDetailsDestination
import com.example.houseplants.ui.screens.PlantGalleryDestination
import com.example.houseplants.ui.screens.PlantGalleryScreen
import com.example.houseplants.ui.screens.SavedPlantsDestination
import com.example.houseplants.ui.screens.SavedScreen
import com.example.houseplants.ui.shareOrder

@Composable
fun PlantsNavHost(
    windowSize: WindowHeightSizeClass,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val plantsViewModel: PlantsViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val plantsUiState = plantsViewModel.plantsUiState
    val savedPlantsUiState by plantsViewModel.savedPlantsUiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = PlantsApp.values().find { it.name == backStackEntry?.destination?.route } ?: PlantsApp.Start
    val context = LocalContext.current

    Scaffold(
        topBar = {
            PlantsAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onShare = {
                    shareOrder(
                        context = context,
                        subject = "Check out this Plants app!",
                        summary = "Check out this Plants app..."
                    )
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = HomeDestination.route) {
                HomeScreen(
                    onExploreButtonClicked = {
                        navController.navigate(PlantGalleryDestination.route)
                    },
                    onSavedButtonClicked = {
                        navController.navigate(SavedPlantsDestination.route)
                    },
                )
            }
            composable(route = PlantGalleryDestination.route) {
                when (plantsUiState) {
                    is PlantsUiState.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                    is PlantsUiState.Success -> PlantGalleryScreen(
                        selectedGroup = plantsUiState.chosenGroup,
                        filter = plantsUiState.chosenFilter,
                        plants = plantsUiState.plants,
                        plantsViewModel = plantsViewModel,
                        onCardClickAction = {
                            navController.navigate("${PlantDetailsDestination.route}/$it")
                        },
                        windowSize = windowSize
                    )
                    is PlantsUiState.Error -> ErrorScreen(
                        plantsViewModel::getPlants,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            composable(
                route = PlantDetailsDestination.routeWithArgs,
                arguments = listOf(navArgument(PlantDetailsDestination.plantDetailIdArg) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val plantId = backStackEntry.arguments?.getInt(PlantDetailsDestination.plantDetailIdArg)
                when (plantsUiState) {
                    is PlantsUiState.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                    is PlantsUiState.Success -> PlantDetailScreen(
                        windowSize = windowSize
                    )
                    is PlantsUiState.Error -> ErrorScreen(
                        plantsViewModel::getPlants,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            composable(route = SavedPlantsDestination.route) {
                SavedScreen(
                    onCardClickAction = {
                        navController.navigate("${PlantDetailsDestination.route}/$it")
                    },
                    savedPlants = savedPlantsUiState.plantList,
                    windowSize = windowSize
                )
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text("Loading")
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Text("Error")
    Button(onClick = retryAction) {
        Text("Retry")
    }
}
