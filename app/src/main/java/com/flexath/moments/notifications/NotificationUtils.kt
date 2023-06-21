package com.flexath.moments.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.flexath.moments.R

object NotificationUtils {

    fun sendNotification(context: Context, body: String, title: String , pendingIntent: PendingIntent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val NOTIFICATION_CHANNEL_ID = "com.flexath.moments" + ".channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.app_name)
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = buildNotification(context, NOTIFICATION_CHANNEL_ID, title, body, pendingIntent)

        notificationManager.notify(getUniqueId(), notification)
    }

    private fun getUniqueId(): Int = ((System.currentTimeMillis() % 10000).toInt())

    private fun buildNotification(
        context: Context,
        channelId: String,
        title: String,
        content: String,
        pendingIntent: PendingIntent
    ): Notification {
        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.bigText(content)

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setStyle(bigTextStyle)
            .setContentIntent(pendingIntent)
            .build()
    }
}