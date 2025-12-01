package com.ah.wallpapers.widget.base

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import com.ah.wallpapers.R

abstract class AppRespWidget : BaseAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Box(
                    modifier = GlanceModifier.fillMaxSize()
                        .clickable(actionStartActivity(getOpenAppIntent(context)))
                ) {
                    Image(
                        provider = ImageProvider(resId = R.drawable.rounder_bg_resp),
                        contentDescription = null,
                        modifier = GlanceModifier.fillMaxSize()
                    )
                    Image(
                        provider = ImageProvider(resId = getAppIconResId()),
                        contentDescription = null,
                        modifier = GlanceModifier.fillMaxSize()
                    )
                }
            }
        }
    }
}