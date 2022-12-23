package ru.rustore.sdk.pushexample.notifications

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import ru.rustore.sdk.pushclient.messaging.exception.RuStorePushClientException
import ru.rustore.sdk.pushclient.messaging.model.RemoteMessage
import ru.rustore.sdk.pushclient.messaging.service.RuStoreMessagingService
import ru.rustore.sdk.pushexample.R
import ru.rustore.sdk.pushexample.notifications.wrapper.NotificationManagerWrapper
import ru.rustore.sdk.pushexample.notifications.wrapper.model.AppNotification
import ru.rustore.sdk.pushexample.notifications.wrapper.model.PushMessageDto

class PushListenerService : RuStoreMessagingService() {
    private val notificationManagerWrapper =
        NotificationManagerWrapper.getInstance(applicationContext)

    override fun onNewToken(token: String) {
        /*
         Вам необходимо отправить полученный пуш-токен на свой сервер,
         с которого будет осуществляться рассылка пуш-уведомлений.
         */
    }

    /*
        Получаем пуш-уведомления и пробуем отобразить контент, полученный в поле data.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.deserializeData() ?: return
        val (channelId, channelName) = getChannelInfo()

        notificationManagerWrapper.showNotification(
            context = this,
            data = AppNotification(
                id = data.hashCode(),
                title = data.title,
                message = data.message,
                channelId = channelId,
                channelName = channelName
            )
        )
    }

    override fun onError(errors: List<RuStorePushClientException>) {
        errors.forEach { error -> error.printStackTrace() }
    }

    private fun RemoteMessage.deserializeData(): PushMessageDto? {
        return try {
            val jsonElement = Json.encodeToJsonElement(data)
            Json.decodeFromJsonElement<PushMessageDto>(jsonElement)
        } catch (e: SerializationException) {
            e.printStackTrace()
            null
        }
    }

    private fun getChannelInfo(): Pair<String, String> {
        val channelId = getString(R.string.notifications_data_push_channel_id)
        val channelName = getString(R.string.notifications_data_push_channel_name)
        return channelId to channelName
    }
}