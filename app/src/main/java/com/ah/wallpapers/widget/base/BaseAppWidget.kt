package com.ah.wallpapers.widget.base

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import com.ah.wallpapers.R
import com.ah.wallpapers.util.openAppIntent
import com.ah.wallpapers.widget.AppAmazonRespWidget
import com.ah.wallpapers.widget.AppAmazonWidget
import com.ah.wallpapers.widget.AppCalculatorRespWidget
import com.ah.wallpapers.widget.AppCalculatorWidget
import com.ah.wallpapers.widget.AppCalendarRespWidget
import com.ah.wallpapers.widget.AppCalendarWidget
import com.ah.wallpapers.widget.AppCameraRespWidget
import com.ah.wallpapers.widget.AppCameraWidget
import com.ah.wallpapers.widget.AppGPTRespWidget
import com.ah.wallpapers.widget.AppGPTWidget

abstract class BaseAppWidget : GlanceAppWidget() {
    protected fun getAppIconResId(): Int {
        return when (this) {
            is AppAmazonWidget -> R.drawable.app_amazon
            is AppAmazonRespWidget -> R.drawable.app_amazon_resp

            is AppCalculatorWidget -> R.drawable.app_calculator
            is AppCalculatorRespWidget -> R.drawable.app_calculator_resp

            is AppCalendarWidget -> R.drawable.app_calendar
            is AppCalendarRespWidget -> R.drawable.app_calendar_resp

            is AppCameraWidget -> R.drawable.app_camera
            is AppCameraRespWidget -> R.drawable.app_camera_resp

            is AppGPTWidget -> R.drawable.app_gpt
            is AppGPTRespWidget -> R.drawable.app_gpt_resp
            else -> error("unsupported type $this")
        }
    }

    protected fun getOpenAppIntent(context: Context): Intent {
        return when (this) {
            is AppAmazonWidget, is AppAmazonRespWidget -> openAmazonIntent(context)
            is AppCalculatorWidget, is AppCalculatorRespWidget -> openCalculator(context)
            is AppCalendarWidget, is AppCalendarRespWidget -> openAppIntent(context, "")
            is AppCameraWidget, is AppCameraRespWidget -> openAppIntent(context, "")
            is AppGPTWidget, is AppGPTRespWidget -> openAppIntent(context, "")
            else -> error("unsupported type $this")
        }
    }

    private fun openAmazonIntent(context: Context): Intent {
        return openAppIntent(context, "com.amazon.mShop.android.shopping")
    }

    private fun openCalculator(context: Context): Intent {
        return openAppIntent(
            context = context,
            intent = Intent().apply {
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_APP_CALCULATOR)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
    }
}