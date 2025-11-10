package com.ah.wallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay
import com.ah.wallpapers.ui.detail.WallpaperDetail
import com.ah.wallpapers.ui.detail.WallpaperDetailScreen
import com.ah.wallpapers.ui.home.HomeScreen
import com.ah.wallpapers.ui.home.HomeViewModel
import com.ah.wallpapers.ui.theme.WallpapersTheme
import kotlinx.serialization.Serializable
import java.lang.IllegalStateException

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavBackStack(Home)
            val localNavSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope> =
                compositionLocalOf {
                    throw IllegalStateException(
                        "Unexpected access to LocalNavSharedTransitionScope. You must provide a " +
                                "SharedTransitionScope from a call to SharedTransitionLayout() or " +
                                "SharedTransitionScope()"
                    )
                }

            val sharedEntryInSceneNavEntryDecorator =
                NavEntryDecorator<Any> { entry ->
                    with(localNavSharedTransitionScope.current) {
                        Box(
                            Modifier.sharedElement(
                                rememberSharedContentState(entry.contentKey),
                                animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                            )
                        ) {
                            entry.Content()
                        }
                    }
                }

            WallpapersTheme {
                SharedTransitionLayout {
                    CompositionLocalProvider(localNavSharedTransitionScope provides this) {
                        NavDisplay(
                            entryDecorators = listOf(
                                sharedEntryInSceneNavEntryDecorator,
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator(),
                            ),
                            backStack = backStack,
                            entryProvider = entryProvider {
                                entry<Home> {
                                    HomeScreen(
                                        sharedScope = this@SharedTransitionLayout,
                                        homeViewModel = viewModel<HomeViewModel>(),
                                        openWallpaperDetail = {
                                            backStack.add(WallpaperDetail(it))
                                        }
                                    )
                                }
                                entry<WallpaperDetail> {
                                    WallpaperDetailScreen(
                                        sharedScope = this@SharedTransitionLayout,
                                        wallpaperDetail = it,
                                        onBack = backStack::removeLastOrNull
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Serializable
data object Home : NavKey