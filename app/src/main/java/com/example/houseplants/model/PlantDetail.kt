package com.example.houseplants.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer


@Serializable
@Entity(tableName="plants")
data class PlantDetail(
    @PrimaryKey
    val id: Int,
    @SerialName("common_name") val commonName: String,
    @SerialName("scientific_name") val scientificName: List<String>? = null,
    @SerialName("other_name") val otherName: List<String>? = null,
    val family: String? = null,
    val origin: List<String>? = null,
    val type: String? = null,
    val dimension: String? = null,
    val dimensions: Dimensions? = null,
    val cycle: String? = null,
    val watering: String? = null,
    @Serializable(with = WaterRequirementSerializer::class)
    @SerialName("depth_water_requirement") val depthWaterRequirement: WaterRequirement? = null,
    @Serializable(with = WaterRequirementSerializer::class)
    @SerialName("volume_water_requirement") val volumeWaterRequirement: WaterRequirement? = null,
    @SerialName("watering_period") val wateringPeriod: String? = null,
    @SerialName("watering_general_benchmark") val wateringGeneralBenchmark: WateringGeneralBenchmark? = null,
    @SerialName("plant_anatomy") val plantAnatomy: List<PlantAnatomy>? = null,
    val sunlight: List<String>? = null,
    @SerialName("pruning_month") val pruningMonth: List<String>? = null,
    val seeds: Int? = null,
    val attracts: List<String>? = null,
    val propagation: List<String>? = null,
    val hardiness: Hardiness? = null,
    @SerialName("hardiness_location") val hardinessLocation: HardinessLocation? = null,
    val flowers: Boolean? = null,
    @SerialName("flowering_season") val floweringSeason: String? = null,
    @SerialName("flower_color") val flowerColor: String? = null,
    val soil: List<String>? = null,
    @SerialName("pest_susceptibility") val pestSusceptibility: List<String>? = null,
    val cones: Boolean? = null,
    val fruits: Boolean? = null,
    @SerialName("edible_fruit") val edibleFruit: Boolean? = null,
    @SerialName("fruit_color") val fruitColor: List<String>? = null,
    @SerialName("fruiting_season") val fruitingSeason: String? = null,
    @SerialName("harvest_season") val harvestSeason: String? = null,
    @SerialName("harvest_method") val harvestMethod: String? = null,
    val leaf: Boolean? = null,
    @SerialName("leaf_color") val leafColor: List<String>? = null,
    @SerialName("edible_leaf") val edibleLeaf: Boolean? = null,
    @SerialName("growth_rate") val growthRate: String? = null,
    val maintenance: String? = null,
    @SerialName("care-guides") val careGuides: String? = null,
    val medicinal: Boolean? = null,
    @SerialName("poisonous_to_humans") val poisonousToHumans: Int? = null,
    @SerialName("poisonous_to_pets") val poisonousToPets: Int? = null,
    @SerialName("drought_tolerant") val droughtTolerant: Boolean? = null,
    @SerialName("salt_tolerant") val saltTolerant: Boolean? = null,
    val thorny: Boolean? = null,
    val invasive: Boolean? = null,
    val rare: Boolean? = null,
    @SerialName("rare_level") val rareLevel: String? = null,
    val tropical: Boolean? = null,
    val cuisine: Boolean? = null,
    val indoor: Boolean? = null,
    @SerialName("care_level") val careLevel: String? = null,
    val description: String? = null,
    @SerialName("default_image") val defaultImage: Image? = null
)

@Serializable
data class Dimensions(
    val type: String? = null,
    @SerialName("min_value") val minValue: Double,
    @SerialName("max_value") val maxValue: Double,
    val unit: String
)


@Serializable
data class WaterRequirement (
    val unit: String? = null,
    val value: String? = null
)

@Serializable
data class WateringGeneralBenchmark(
    val value: String? = null,
    val unit: String? = null
)

@Serializable
data class PlantAnatomy(
    val part: String,
    val color: List<String>
)

@Serializable
data class Hardiness(
    val min: String,
    val max: String
)

@Serializable
data class HardinessLocation(
    @SerialName("full_url") val fullUrl: String,
    @SerialName("full_iframe") val fullIframe: String
)

object WaterRequirementSerializer : JsonTransformingSerializer<WaterRequirement>(WaterRequirement.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return when {
            element is JsonObject -> {
                element
            }
            element is JsonArray && element.isEmpty() -> {
                JsonObject(emptyMap())
            }
            else -> {
                JsonObject(emptyMap())
            }
        }
    }
}






