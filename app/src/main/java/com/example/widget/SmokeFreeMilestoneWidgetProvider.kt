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

class SmokeFreeMilestoneWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val prefs = JourneyPreferences(context)
            val profile = prefs.getProfile()
            val now = System.currentTimeMillis()

            val views = RemoteViews(context.packageName, R.layout.widget_smoke_free_ribbon)

            val days = profile.days(now)
            val hours = profile.hours(now)
            val totalSavedFormatted = profile.formatCurrency(profile.totalMoneySaved(now))
            val isWindowActive = profile.isSmokeWindowActive(now)

            views.setTextViewText(R.id.ribbon_text_days, days.toString())
            views.setTextViewText(R.id.ribbon_label_days, if (days == 1L) "DAY" else "DAYS")
            views.setTextViewText(R.id.ribbon_text_hours, hours.toString())
            views.setTextViewText(R.id.ribbon_label_hours, if (hours == 1L) "HOUR" else "HOURS")

            views.setTextViewText(
                R.id.ribbon_text_badge,
                if (isWindowActive) "URGE WINDOW" else "NICOTINE FREE"
            )
            views.setTextViewText(R.id.ribbon_text_sub, "$totalSavedFormatted Saved")

            if (isWindowActive) {
                val remSeconds = profile.smokeWindowRemainingSeconds(now)
                val mins = remSeconds / 60
                val secs = remSeconds % 60
                views.setTextViewText(R.id.ribbon_text_window, "Wave: ${mins}m ${secs}s")
            } else {
                views.setTextViewText(R.id.ribbon_text_window, "Smoke Window: Ready")
            }

            // Click opens app
            val appIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val appPendingIntent = PendingIntent.getActivity(
                context,
                20,
                appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_ribbon_root, appPendingIntent)

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
            val thisWidget = ComponentName(context, SmokeFreeMilestoneWidgetProvider::class.java)
            val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            updateWidgets(context, appWidgetManager, allWidgetIds)
        }
    }
}
