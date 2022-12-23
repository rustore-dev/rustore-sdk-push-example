package ru.rustore.sdk.pushexample.notifications.wrapper

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.rustore.sdk.pushexample.notifications.wrapper.model.AppNotification

class NotificationManagerWrapper private constructor(
    private val notificationManager: NotificationManagerCompat
) {
    fun createNotificationChannel(channelId: String, channelName: String) {
        val channel = NotificationChannelCompat
            .Builder(channelId, NotificationManagerCompat.IMPORTANCE_DEFAULT)
            .setName(channelName)
            .build()
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(context: Context, data: AppNotification) {
        val notification = NotificationCompat.Builder(context, data.channelId)
            .setContentTitle(data.title)
            .setContentText(data.message)
            .build()

        if (notificationManager.getNotificationChannel(data.channelId) == null) {
            createNotificationChannel(channelId = data.channelId, channelName = data.channelName)
        }

        notificationManager.notify(data.id, notification)
    }

    companion object {
        private var instance: NotificationManagerWrapper? = null

        fun getInstance(context: Context): NotificationManagerWrapper {
            return instance ?: NotificationManagerWrapper(
                notificationManager = NotificationManagerCompat.from(context)
            ).also { instance = it }
        }
    }
}