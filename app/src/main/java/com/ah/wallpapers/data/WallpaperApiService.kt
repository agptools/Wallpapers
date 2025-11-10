package com.ah.wallpapers.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

@Serializable
data class WallpaperResponse(
    @SerialName("categories") val categories: List<Category>,
    @SerialName("wallpapers") val wallpapers: List<Wallpaper>
)

@Serializable
data class Category(
    @SerialName("category_id") val categoryId: Int,
    @SerialName("category_image") val categoryImage: String,
    @SerialName("category_name") val categoryName: String
)

@Serializable
data class Wallpaper(
    @SerialName("category_ids") val categoryIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("name") val name: String,
    @SerialName("thumbnail_url") val thumbnailUrl: String
)

interface WallpaperApiService {
    @GET("TaiyabMachhaliy/JSON/refs/heads/main/material_you_final.json")
    suspend fun getWallpapers(): WallpaperResponse
}