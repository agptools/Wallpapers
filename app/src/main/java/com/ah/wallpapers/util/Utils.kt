package com.ah.wallpapers.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openAppIntent(context: Context, packageName: String): Intent {
    val intent = context.packageManager.getLaunchIntentForPackage(packageName) ?: Intent("android.intent.action.VIEW").apply {
        data = "market://details?id=$packageName".toUri()
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}

fun openAppIntent(context: Context, intent: Intent): Intent {
    return if (intent.resolveActivity(context.packageManager) != null) {
        intent
    } else {
        Intent()
    }
}