package com.example.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent

object WidgetUpdateHelper {
    fun updateAllWidgets(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)

        // Large Widget
        val largeComponent = ComponentName(context, SmokeFreeWidgetProvider::class.java)
        val largeIds = appWidgetManager.getAppWidgetIds(largeComponent)
        if (largeIds != null && largeIds.isNotEmpty()) {
            SmokeFreeWidgetProvider.updateWidgets(context, appWidgetManager, largeIds)
        }

        // Compact Widget
        val compactComponent = ComponentName(context, SmokeFreeCompactWidgetProvider::class.java)
        val compactIds = appWidgetManager.getAppWidgetIds(compactComponent)
        if (compactIds != null && compactIds.isNotEmpty()) {
            SmokeFreeCompactWidgetProvider.updateWidgets(context, appWidgetManager, compactIds)
        }

        // 4x1 Bold Ribbon Widget
        val ribbonComponent = ComponentName(context, SmokeFreeMilestoneWidgetProvider::class.java)
        val ribbonIds = appWidgetManager.getAppWidgetIds(ribbonComponent)
        if (ribbonIds != null && ribbonIds.isNotEmpty()) {
            SmokeFreeMilestoneWidgetProvider.updateWidgets(context, appWidgetManager, ribbonIds)
        }
    }
}
