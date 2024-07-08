package com.example.houseplants.data

import androidx.room.TypeConverter
import com.example.houseplants.model.Dimensions
import com.example.houseplants.model.Hardiness
import com.example.houseplants.model.HardinessLocation
import com.example.houseplants.model.Image
import com.example.houseplants.model.PlantAnatomy
import com.example.houseplants.model.WaterRequirement
import com.example.houseplants.model.WateringGeneralBenchmark
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return value?.joinToString(", ") ?: ""
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        return if (value.isEmpty()) null else value.split(", ")
    }

    @TypeConverter
    fun fromPlantAnatomyList(value: List<PlantAnatomy>?): String {
        return value?.joinToString(";") { "${it.part}:${it.color.joinToString(",")}" } ?: ""
    }

    @TypeConverter
    fun toPlantAnatomyList(value: String): List<PlantAnatomy>? {
        return if (value.isEmpty()) null else value.split(";").map {
            val (part, colors) = it.split(":")
            PlantAnatomy(part, colors.split(","))
        }
    }

    @TypeConverter
    fun fromDimensions(value: Dimensions?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toDimensions(value: String): Dimensions? {
        return if (value.isEmpty()) null else Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromWaterRequirement(value: WaterRequirement?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toWaterRequirement(value: String): WaterRequirement? {
        return if (value.isEmpty()) null else Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromWateringGeneralBenchmark(value: WateringGeneralBenchmark?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toWateringGeneralBenchmark(value: String): WateringGeneralBenchmark? {
        return if (value.isEmpty()) null else Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromHardiness(value: Hardiness?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toHardiness(value: String): Hardiness? {
        return if (value.isEmpty()) null else Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromHardinessLocation(value: HardinessLocation?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toHardinessLocation(value: String): HardinessLocation? {
        return if (value.isEmpty()) null else Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromImage(value: Image?): String {
        return value?.let { Json.encodeToString(it) } ?: ""
    }

    @TypeConverter
    fun toImage(value: String): Image? {
        return if (value.isEmpty()) null else Json.decodeFromString(value)
    }
}