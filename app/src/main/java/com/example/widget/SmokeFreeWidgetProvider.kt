package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.example.MainActivity
import com.example.R
import com.example.data.JourneyPreferences
import com.example.data.JourneyProfile

class SmokeFreeWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_REFRESH_WIDGET = "com.example.ACTION_REFRESH_WIDGET"

        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val prefs = JourneyPreferences(context)
            val profile = prefs.getProfile()
            val now = System.currentTimeMillis()

            val views = RemoteViews(context.packageName, R.layout.widget_smoke_free)

            val days = profile.days(now)
            val hours = profile.hours(now)
            val dailyCostFormatted = profile.formatCurrency(profile.dailyCost)
            val totalSavedFormatted = profile.formatCurrency(profile.totalMoneySaved(now))
            val cigsAvoided = profile.cigarettesAvoided(now)

            // Populate view values
            views.setTextViewText(R.id.widget_text_days, days.toString())
            views.setTextViewText(R.id.widget_text_hours, hours.toString())
            views.setTextViewText(
                R.id.widget_label_hours,
                if (hours == 1L) "HOUR" else "HOURS"
            )
            views.setTextViewText(R.id.widget_text_daily_saved, "$dailyCostFormatted / day")
            views.setTextViewText(R.id.widget_text_total_saved, "$totalSavedFormatted saved")
            views.setTextViewText(R.id.widget_text_cigarettes, "$cigsAvoided not smoked")

            // Main app intent
            val appIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val appPendingIntent = PendingIntent.getActivity(
                context,
                0,
                appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, appPendingIntent)

            // Refresh intent
            val refreshIntent = Intent(context, SmokeFreeWidgetProvider::class.java).apply {
                action = ACTION_REFRESH_WIDGET
            }
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_btn_refresh, refreshPendingIntent)

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
        if (intent.action == ACTION_REFRESH_WIDGET) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, SmokeFreeWidgetProvider::class.java)
            val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            updateWidgets(context, appWidgetManager, allWidgetIds)
            Toast.makeText(context, "Smoke-Free stats refreshed!", Toast.LENGTH_SHORT).show()
        }
    }
}
