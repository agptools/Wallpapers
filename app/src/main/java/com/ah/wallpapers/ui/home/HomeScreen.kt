package com.ah.wallpapers.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ah.wallpapers.data.Wallpaper
import com.ah.wallpapers.data.WallpaperCategory

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun HomeScreen(
    sharedScope: SharedTransitionScope,
    homeViewModel: HomeViewModel,
    openWallpaperDetail: (Wallpaper) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        CenterAlignedTopAppBar(title = { Text("Wallpaper".uppercase()) })
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.isLoading -> LoadingIndicator(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                )

                uiState.categories.isEmpty() -> Text("Empty")

                else -> {
                    WallpaperList(
                        sharedScope = sharedScope,
                        uiState = uiState,
                        onTabSelect = homeViewModel::onSelectIndex,
                        onItemClick = openWallpaperDetail,
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryTab(
    modifier: Modifier = Modifier,
    category: WallpaperCategory,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val primaryColor = MaterialTheme.colorScheme.primary
        AsyncImage(
            model = category.categoryImage,
            contentDescription = category.categoryName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(70.dp)
                .clip(RoundedCornerShape(45.dp))
                .drawWithContent {
                    drawContent()
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0x00000000),
                                Color(0x80000000)
                            )
                        )
                    )
                    if (selected) {
                        drawCircle(primaryColor, style = Stroke(4.dp.toPx()))
                    }
                }
                .clickable(onClick = onClick)
        )
        Text(
            text = category.categoryName,
            style = LocalTextStyle.current.copy(fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
        )
    }
}

@OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
private fun WallpaperList(
    sharedScope: SharedTransitionScope,
    uiState: HomeUiState,
    onTabSelect: (Int) -> Unit,
    onItemClick: (Wallpaper) -> Unit,
) {
    Column {
        PrimaryScrollableTabRow(
            selectedTabIndex = uiState.selectIndex,
            edgePadding = 10.dp,
            divider = {},
            indicator = {},
        ) {
            uiState.categories.forEachIndexed { index, category ->
                CategoryTab(
                    category = category,
                    selected = index == uiState.selectIndex,
                    onClick = { onTabSelect(index) },
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Crossfade(
            targetState = uiState.selectIndex
        ) { index ->
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 10.dp,
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = uiState.categories[index].wallpapers,
                    key = { it.id }
                ) { wallpaper ->
                    with(sharedScope) {
                        val clipShape = RoundedCornerShape(24.dp)
                        Card(
                            modifier = Modifier
                                .sharedBounds(
                                    sharedScope.rememberSharedContentState(key = wallpaper.thumbnailUrl),
                                    animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                                    clipInOverlayDuringTransition = OverlayClip(clipShape)
                                )
                                .clickable {
                                    onItemClick(wallpaper)
                                },
                            shape = clipShape
                        ) {
                            AsyncImage(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(wallpaper.thumbnailUrl)
                                    .placeholderMemoryCacheKey(wallpaper.thumbnailUrl)
                                    .memoryCacheKey(wallpaper.thumbnailUrl)
                                    .build(),
                                contentDescription = wallpaper.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(275.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}