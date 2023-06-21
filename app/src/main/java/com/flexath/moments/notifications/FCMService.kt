package com.flexath.moments.notifications

import android.app.PendingIntent
import android.util.Log
import com.flexath.moments.activities.ChatDetailActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if(message.data["chat_type"] == "private") {

            NotificationUtils.sendNotification(
                this,
                title = message.data["title"] ?: "",
                body = message.data["body"] ?: "",
                pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    ChatDetailActivity.newIntent(this,message.data["chat_id"] ?: "",""),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
                )
            )
        } else {
            NotificationUtils.sendNotification(
                this,
                title = message.data["title"] ?: "",
                body = message.data["body"] ?: "",
                pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    ChatDetailActivity.newIntent(this,"",message.data["chat_id"] ?: ""),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
                )
            )
        }


    }
}