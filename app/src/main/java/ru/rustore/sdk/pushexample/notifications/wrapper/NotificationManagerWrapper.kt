package ru.rustore.sdk.pushexample.notifications.wrapper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
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
        val notification = data.channelId?.let {
            NotificationCompat.Builder(context, it)
                .setContentTitle(data.title)
                .setContentText(data.message)
                .build()
        }

        if (notificationManager.getNotificationChannel(data.channelId.toString()) == null) {
            createNotificationChannel(channelId = data.channelId.toString(), channelName = data.channelName.toString())
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (notification != null) {
            notificationManager.notify(data.id, notification)
        }
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