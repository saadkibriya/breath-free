package com.example.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.data.JourneyPreferences
import com.example.widget.WidgetUpdateHelper

object SmokeWindowHelper {

    fun startSmokeWindow(context: Context, durationMinutes: Int = 10): Long {
        val now = System.currentTimeMillis()
        val endTime = now + (durationMinutes * 60 * 1000L)

        // Save in preferences
        val prefs = JourneyPreferences(context)
        val profile = prefs.getProfile()
        prefs.saveProfile(profile.copy(smokeWindowEndTime = endTime))

        // Schedule Alarm
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, SmokeWindowReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            5050,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager?.let { am ->
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, endTime, pendingIntent)
                } else {
                    am.set(AlarmManager.RTC_WAKEUP, endTime, pendingIntent)
                }
            } catch (e: SecurityException) {
                // In case exact alarm permission is restricted on Android 12+, fallback to inexact set
                am.set(AlarmManager.RTC_WAKEUP, endTime, pendingIntent)
            }
        }

        WidgetUpdateHelper.updateAllWidgets(context)
        return endTime
    }

    fun cancelSmokeWindow(context: Context) {
        val prefs = JourneyPreferences(context)
        val profile = prefs.getProfile()
        prefs.saveProfile(profile.copy(smokeWindowEndTime = 0L))

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, SmokeWindowReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            5050,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager?.cancel(pendingIntent)

        WidgetUpdateHelper.updateAllWidgets(context)
    }
}
