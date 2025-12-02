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
import com.ah.wallpapers.widget.AppCalendarRespWidget
import com.ah.wallpapers.widget.AppCalendarRespWidgetReceiver
import com.ah.wallpapers.widget.AppCalendarWidget
import com.ah.wallpapers.widget.AppCalendarWidgetReceiver
import com.ah.wallpapers.widget.AppCameraRespWidget
import com.ah.wallpapers.widget.AppCameraRespWidgetReceiver
import com.ah.wallpapers.widget.AppCameraWidget
import com.ah.wallpapers.widget.AppCameraWidgetReceiver
import com.ah.wallpapers.widget.AppGPTRespWidget
import com.ah.wallpapers.widget.AppGPTRespWidgetReceiver
import com.ah.wallpapers.widget.AppGPTWidget
import com.ah.wallpapers.widget.AppGPTWidgetReceiver

abstract class BaseAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = when (this) {
        is AppAmazonWidgetReceiver -> AppAmazonWidget()
        is AppAmazonRespWidgetReceiver -> AppAmazonRespWidget()

        is AppCalculatorWidgetReceiver -> AppCalculatorWidget()
        is AppCalculatorRespWidgetReceiver -> AppCalculatorRespWidget()

        is AppCalendarWidgetReceiver -> AppCalendarWidget()
        is AppCalendarRespWidgetReceiver -> AppCalendarRespWidget()

        is AppCameraWidgetReceiver -> AppCameraWidget()
        is AppCameraRespWidgetReceiver -> AppCameraRespWidget()

        is AppGPTWidgetReceiver -> AppGPTWidget()
        is AppGPTRespWidgetReceiver -> AppGPTRespWidget()

        else -> error("not supported type $this")
    }
}