package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.MainActivity
import com.example.R
import com.example.data.JourneyPreferences

class SmokeFreeCompactWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val prefs = JourneyPreferences(context)
            val profile = prefs.getProfile()
            val now = System.currentTimeMillis()

            val views = RemoteViews(context.packageName, R.layout.widget_smoke_free_compact)

            val days = profile.days(now)
            val hours = profile.hours(now)
            val dailyCostFormatted = profile.formatCurrency(profile.dailyCost)
            val totalSavedFormatted = profile.formatCurrency(profile.totalMoneySaved(now))

            views.setTextViewText(R.id.widget_text_days, days.toString())
            views.setTextViewText(R.id.widget_text_hours_compact, "DAYS • ${hours}H")
            views.setTextViewText(R.id.widget_text_daily_saved, "$dailyCostFormatted / day")
            views.setTextViewText(R.id.widget_text_total_saved, "$totalSavedFormatted saved")

            val appIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val appPendingIntent = PendingIntent.getActivity(
                context,
                10,
                appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, appPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun updateWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            for (widgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, widgetId)
            }
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        updateWidgets(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == SmokeFreeWidgetProvider.ACTION_REFRESH_WIDGET) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, SmokeFreeCompactWidgetProvider::class.java)
            val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            updateWidgets(context, appWidgetManager, allWidgetIds)
        }
    }
}
