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

abstract class BaseAppWidget : GlanceAppWidget() {
    protected fun getAppIconResId(): Int {
        return when (this) {
            is AppAmazonWidget -> R.drawable.app_amazon
            is AppAmazonRespWidget -> R.drawable.app_amazon_resp
            is AppCalculatorWidget -> R.drawable.app_calculator
            is AppCalculatorRespWidget -> R.drawable.app_calculator_resp
            else -> error("unsupported type $this")
        }
    }

    protected fun getOpenAppIntent(context: Context): Intent {
        Intent.CATEGORY_APP_CALCULATOR
        return when (this) {
            is AppAmazonWidget, is AppAmazonRespWidget -> openAmazonIntent(context)
            is AppCalculatorWidget, is AppCalculatorRespWidget -> openCalculator(context)
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