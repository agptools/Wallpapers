package com.ah.wallpapers.widget.base

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.ah.wallpapers.widget.AppAmazonRespWidget
import com.ah.wallpapers.widget.AppAmazonRespWidgetReceiver
import com.ah.wallpapers.widget.AppAmazonWidget
import com.ah.wallpapers.widget.AppAmazonWidgetReceiver
import com.ah.wallpapers.widget.AppCalculatorRespWidget
import com.ah.wallpapers.widget.AppCalculatorRespWidgetReceiver
import com.ah.wallpapers.widget.AppCalculatorWidget
import com.ah.wallpapers.widget.AppCalculatorWidgetReceiver

abstract class BaseAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = when (this) {
        is AppAmazonWidgetReceiver -> AppAmazonWidget()
        is AppAmazonRespWidgetReceiver -> AppAmazonRespWidget()
        is AppCalculatorWidgetReceiver -> AppCalculatorWidget()
        is AppCalculatorRespWidgetReceiver -> AppCalculatorRespWidget()
        else -> error("not supported type $this")
    }
}