package com.ah.wallpapers.ui.detail

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ah.wallpapers.R
import com.ah.wallpapers.data.Wallpaper
import com.ah.wallpapers.ui.theme.mapleFamily
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        with(sharedScope) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(wallpaperDetail.wallpaper.imageUrl)
                    .placeholderMemoryCacheKey(wallpaperDetail.wallpaper.thumbnailUrl)
                    .memoryCacheKey(wallpaperDetail.wallpaper.thumbnailUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .sharedBounds(
                        sharedScope.rememberSharedContentState(key = wallpaperDetail.wallpaper.thumbnailUrl),
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ),
            )
        }

        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "WallPaper".uppercase(),
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBack,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 30.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontFamily = mapleFamily)
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
            ) {
                Text("Download".uppercase(), style = textStyle)
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {}
            ) {
                Text("Set".uppercase(), style = textStyle)
            }
        }
    }
}