package com.ah.wallpapers.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

data class WallpaperCategory(
    val categoryId: Int,
    val categoryImage: String,
    val categoryName: String,
    val wallpapers: List<Wallpaper>
)

class WallpaperRepository {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(
                Json.asConverterFactory(
                    "application/json; charset=UTF-8".toMediaType()
                )
            )
            .build()
    }

    private val apiService by lazy { retrofit.create<WallpaperApiService>() }

    suspend fun getWallpapers(): Result<List<WallpaperCategory>> = withContext(Dispatchers.IO) {
        runCatching {
            apiService.getWallpapers()
        }.map { res ->
            res.categories.map { category ->
                WallpaperCategory(
                    categoryId = category.categoryId,
                    categoryImage = category.categoryImage,
                    categoryName = category.categoryName,
                    wallpapers = res.wallpapers.filter { it.categoryIds.contains(category.categoryId) }
                )
            }
        }
    }
}