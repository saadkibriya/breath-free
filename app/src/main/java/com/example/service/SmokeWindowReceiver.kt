package com.example.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.widget.WidgetUpdateHelper

class SmokeWindowReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "smoke_window_channel"
        const val NOTIFICATION_ID = 4040
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel on Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Smoke Window & Craving Urge Alarms",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies you when your 10-minute craving delay wave has safely passed."
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Tap opens app
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            202,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sprout)
            .setContentTitle("⏰ 10-Min Urge Window Complete!")
            .setContentText("The craving wave has crested & collapsed! You proved your mastery over nicotine.")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "🎉 Outstanding discipline! Biological addiction research proves nicotine urge spikes dissolve after 8–10 minutes. By pausing and holding the line, your neuroreceptors have down-regulated another step. Keep going!"
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)

        // Refresh widgets to show window finished
        WidgetUpdateHelper.updateAllWidgets(context)
    }
}
