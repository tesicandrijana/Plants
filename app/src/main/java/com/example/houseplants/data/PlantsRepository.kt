package com.example.houseplants.data

import com.example.houseplants.model.PlantDetail
import com.example.houseplants.model.PlantListResponse
import com.example.houseplants.network.PlantsApiService
import kotlinx.coroutines.flow.Flow

interface PlantsRepository {
    suspend fun getPoisonousPlants(): PlantListResponse
    suspend fun getEdiblePlants(): PlantListResponse
    suspend fun getIndoorsPlants(): PlantListResponse
    suspend fun getOutdoorsPlants(): PlantListResponse
    suspend fun getPlantDetail(plantId: Int): PlantDetail

    /*
    * Dohvati sve biljke
    * */
    fun getAllPlantsStream(): Flow<List<PlantDetail>>

    /*
    * Dohvati biljku
    * */
    fun getPlantStream(id: Int): Flow<PlantDetail>

    /*
    * Dodaj biljku u bazu
    * */
    suspend fun insertPlant(plantDetail: PlantDetail)

    /*
    * Obriši biljku iz baze
    * */
    suspend fun deletePlant(plantDetail: PlantDetail)
}

class NetworkPlantsRepository(
    private val plantsApiService: PlantsApiService,
    private val plantDetailDao: PlantDetailDao
) : PlantsRepository {
    override suspend fun getPoisonousPlants(): PlantListResponse = plantsApiService.getPoisonousPlants()
    override suspend fun getEdiblePlants(): PlantListResponse = plantsApiService.getEdiblePlants()
    override suspend fun getIndoorsPlants(): PlantListResponse = plantsApiService.getIndoorsPlants()
    override suspend fun getOutdoorsPlants(): PlantListResponse = plantsApiService.getOutdoorsPlants()
    override suspend fun getPlantDetail(plantId: Int): PlantDetail = plantsApiService.getPlantDetail(plantId)

    override fun getAllPlantsStream(): Flow<List<PlantDetail>> = plantDetailDao.getAllPlants()

    override fun getPlantStream(id: Int): Flow<PlantDetail> = plantDetailDao.getPlant(id)

    override suspend fun insertPlant(plantDetail: PlantDetail) = plantDetailDao.insert(plantDetail)

    override suspend fun deletePlant(plantDetail: PlantDetail) = plantDetailDao.delete(plantDetail)

}




