package com.example.houseplants.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class PlantListResponse(
    val data: List<Plant>,
    val to: Int,
    val per_page: Int,
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val total: Int
)

@Serializable
data class Plant(
    val id: Int,
    @SerialName("common_name") val commonName: String,
    @SerialName("scientific_name") val scientificName: List<String>? = null,
    @SerialName("other_name") val otherName: List<String>? = null,
    val cycle: String? = null,
    val watering: String? = null,
    val sunlight: List<String>? = null,
    @SerialName("default_image") val defaultImage: Image? = null,
)

@Serializable
data class Image(
    @SerialName("image_id") val imageId: Int? = null,
    val license: Int,
    @SerialName("license_name") val licenseName: String? = null,
    @SerialName("license_url") val licenseUrl: String? = null,
    @SerialName("original_url") val originalUrl: String? = null,
    @SerialName("regular_url") val regularUrl: String? = null,
    @SerialName("medium_url") val mediumUrl: String? = null,
    @SerialName("small_url") val smallUrl: String? = null,
    val thumbnail: String? = null
)