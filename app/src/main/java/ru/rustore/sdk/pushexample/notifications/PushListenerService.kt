package ru.rustore.sdk.pushexample.notifications

import android.util.Log
import ru.rustore.sdk.pushclient.messaging.exception.RuStorePushClientException
import ru.rustore.sdk.pushclient.messaging.model.RemoteMessage
import ru.rustore.sdk.pushclient.messaging.service.RuStoreMessagingService
import ru.rustore.sdk.pushexample.R
import ru.rustore.sdk.pushexample.notifications.wrapper.NotificationManagerWrapper
import ru.rustore.sdk.pushexample.notifications.wrapper.model.AppNotification

class PushListenerService : RuStoreMessagingService() {
    private val notificationManagerWrapper =
        NotificationManagerWrapper.getInstance(this)

    override fun onNewToken(token: String) {
        Log.d(LOG_TAG, "onNewToken token = $token")
        /*
         Вам необходимо отправить полученный пуш-токен на свой сервер,
         с которого будет осуществляться рассылка пуш-уведомлений.
         */
    }

    /*
        Получаем пуш-уведомления и пробуем отобразить контент, полученный в поле data.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        val (channelId, channelName) = getChannelInfo()

        notificationManagerWrapper.showNotification(
            context = this,
            data = AppNotification(
                id = message.hashCode(),
                title = message.notification?.title,
                message = message.notification?.body,
                channelId = channelId,
                channelName = channelName
            )
        )
    }

    override fun onError(errors: List<RuStorePushClientException>) {
        errors.forEach { error -> error.printStackTrace() }
    }

    private fun getChannelInfo(): Pair<String, String> {
        val channelId = getString(R.string.notifications_data_push_channel_id)
        val channelName = getString(R.string.notifications_data_push_channel_name)
        return channelId to channelName
    }

    companion object {
        private const val LOG_TAG = "PushListenerService"
    }
}