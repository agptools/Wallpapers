package com.ah.wallpapers.ui.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.media.MediaScannerConnection
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import java.util.Locale

suspend fun saveImageToGallery(context: Context, name: String, bitmap: Bitmap?) = withContext(Dispatchers.IO) {
    runCatching {
        if (bitmap == null) {
            return@runCatching false
        }
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Wallpapers")
        if (!file.exists() && !file.mkdirs()) {
            return@runCatching false
        }
        val file2 = File(file, "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}_$name.png")
        val fileOutputStream = FileOutputStream(file2)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        MediaScannerConnection.scanFile(context, arrayOf(file2.absolutePath), arrayOf("image/png"), null)
        true
    }
}

suspend fun setImageToWallpaper(context: Context, bitmap: Bitmap?, which: Int) = withContext(Dispatchers.IO) {
    runCatching {
        if (bitmap == null) {
            return@runCatching false
        }
        WallpaperManager.getInstance(context).setBitmap(bitmap, null, true, which) != 0
    }
}