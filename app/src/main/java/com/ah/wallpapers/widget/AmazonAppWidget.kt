package com.ah.wallpapers.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import com.ah.wallpapers.R

class AmazonAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Box(GlanceModifier.fillMaxSize()) {
                    Image(
                        provider = ImageProvider(resId = R.drawable.rounder_glow2),
                        contentDescription = null,
                        modifier = GlanceModifier.fillMaxSize()
                    )
                    Image(
                        provider = ImageProvider(resId = R.drawable.amazon_resp),
                        contentDescription = null,
                        modifier = GlanceModifier.fillMaxSize()
                    )
                }
            }
        }
    }
}