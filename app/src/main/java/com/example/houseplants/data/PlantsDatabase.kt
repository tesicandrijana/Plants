package com.example.houseplants.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.houseplants.model.PlantDetail

@Database(entities = [PlantDetail::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlantsDatabase : RoomDatabase() {
    abstract fun plantDetailDao() : PlantDetailDao



    companion object {

        @Volatile
        private var Instance: PlantsDatabase? = null
        fun getDatabase(context: Context): PlantsDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PlantsDatabase::class.java, "plants_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}