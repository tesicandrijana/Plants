package com.example.houseplants.network

import com.example.houseplants.model.PlantDetail
import com.example.houseplants.model.PlantListResponse
import retrofit2.http.GET
import retrofit2.http.Path



interface PlantsApiService{

    companion object {
        const val API_KEY = "sk-xhuO6678ca7fe85a96019"
    }

    @GET("species-list?key=$API_KEY&poisonous=1")
    suspend fun getPoisonousPlants() : PlantListResponse

    @GET("species-list?key=$API_KEY&edible=1")
    suspend fun getEdiblePlants() : PlantListResponse

    @GET("species-list?key=$API_KEY&indoor=1")
    suspend fun getIndoorsPlants() : PlantListResponse

    @GET("species-list?key=$API_KEY&indoor=0")
    suspend fun getOutdoorsPlants() : PlantListResponse

    @GET("species/details/{plantId}?key=$API_KEY")
    suspend fun getPlantDetail(@Path("plantId") plantId: Int): PlantDetail

}



