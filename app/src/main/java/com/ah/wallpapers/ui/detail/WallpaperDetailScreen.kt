package com.ah.wallpapers.ui.detail

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import com.ah.wallpapers.R
import com.ah.wallpapers.data.Wallpaper
import com.ah.wallpapers.ui.component.FullscreenLoadingIndicator
import com.ah.wallpapers.ui.theme.mapleFamily
import com.ah.wallpapers.ui.utils.saveImageToGallery
import com.ah.wallpapers.ui.utils.setImageToWallpaper
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperDetail(
    val wallpaper: Wallpaper
) : NavKey

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun WallpaperDetailScreen(
    sharedScope: SharedTransitionScope,
    wallpaperDetail: WallpaperDetail,
    onBack: () -> Unit,
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isShowDialog by remember { mutableStateOf(false) }
    var isShowLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val appContext = LocalContext.current.applicationContext
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "WallPaper".uppercase(),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                )
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(bottom = 80.dp)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            with(sharedScope) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(wallpaperDetail.wallpaper.imageUrl)
                        .allowHardware(false)
                        .placeholderMemoryCacheKey(wallpaperDetail.wallpaper.thumbnailUrl)
                        .memoryCacheKey(wallpaperDetail.wallpaper.thumbnailUrl)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    onSuccess = {
                        bitmap = it.result.image.toBitmap()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedBounds(
                            sharedScope.rememberSharedContentState(key = wallpaperDetail.wallpaper.thumbnailUrl),
                            animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                        ),
                )
            }

            if (isShowLoading) {
                FullscreenLoadingIndicator()
            }

            if (isShowDialog) {
                SetWallpaperDialog(
                    onDismissRequest = { isShowDialog = false },
                    onHomeScreen = {
                        coroutineScope.launch {
                            isShowDialog = false
                            isShowLoading = true
                            val success = setImageToWallpaper(appContext, bitmap, WallpaperManager.FLAG_SYSTEM).getOrNull() ?: false
                            isShowLoading = false
                            if (success) {
                                snackBarHostState.showSnackbar("Wallpaper set successfully")
                            } else {
                                snackBarHostState.showSnackbar("Failed to set wallpaper")
                            }
                        }
                    },
                    onLockScreen = {
                        coroutineScope.launch {
                            isShowDialog = false
                            isShowLoading = true
                            val success = setImageToWallpaper(appContext, bitmap, WallpaperManager.FLAG_LOCK).getOrNull() ?: false
                            isShowLoading = false
                            if (success) {
                                snackBarHostState.showSnackbar("Wallpaper set successfully")
                            } else {
                                snackBarHostState.showSnackbar("Failed to set wallpaper")
                            }
                        }
                    },
                    onBoth = {
                        coroutineScope.launch {
                            isShowDialog = false
                            isShowLoading = true
                            val success = setImageToWallpaper(appContext, bitmap, WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM).getOrNull() ?: false
                            isShowLoading = false
                            if (success) {
                                snackBarHostState.showSnackbar("Wallpaper set successfully")
                            } else {
                                snackBarHostState.showSnackbar("Failed to set wallpaper")
                            }
                        }
                    }
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .padding(bottom = 30.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                val textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontFamily = mapleFamily)
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        coroutineScope.launch {
                            isShowLoading = true
                            val success = saveImageToGallery(appContext, wallpaperDetail.wallpaper.name, bitmap).getOrNull() ?: false
                            isShowLoading = false
                            if (success) {
                                snackBarHostState.showSnackbar("Wallpaper saved to gallery")
                            } else {
                                snackBarHostState.showSnackbar("Failed to save wallpaper")
                            }
                        }
                    },
                ) {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    Text("Download".uppercase(), style = textStyle)
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { isShowDialog = true }
                ) {
                    Text("Set".uppercase(), style = textStyle)
                }
            }
        }
    }
}

@Composable
private fun SetWallpaperDialog(
    onDismissRequest: () -> Unit,
    onHomeScreen: () -> Unit,
    onLockScreen: () -> Unit,
    onBoth: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text("Set Wallpaper".uppercase(), style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Home Screen",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(onClick = onHomeScreen)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = "Lock Screen",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(onClick = onLockScreen)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = "Both",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clickable(onClick = onBoth)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}