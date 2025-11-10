package com.ah.wallpapers

import com.ah.wallpapers.data.WallpaperRepository

object AppContainer {
    val wallpaperRepository by lazy { WallpaperRepository() }
}