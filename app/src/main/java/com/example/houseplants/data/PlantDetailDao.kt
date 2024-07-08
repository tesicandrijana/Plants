package com.example.houseplants.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.houseplants.model.PlantDetail
import kotlinx.coroutines.flow.Flow


@Dao
interface PlantDetailDao{

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 suspend fun insert(plantDetail: PlantDetail)

 @Delete
 suspend fun delete(plantDetail: PlantDetail)

 @Query("SELECT * from plants WHERE id = :id")
 fun getPlant(id: Int): Flow<PlantDetail>

 @Query("SELECT * from plants")
 fun getAllPlants(): Flow<List<PlantDetail>>


}
