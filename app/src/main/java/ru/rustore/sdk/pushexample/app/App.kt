package ru.rustore.sdk.pushexample.app

import android.app.Application
import android.util.Log
import ru.rustore.sdk.core.tasks.OnCompleteListener
import ru.rustore.sdk.pushclient.RuStorePushClient
import ru.rustore.sdk.pushexample.BuildConfig
import ru.rustore.sdk.pushexample.R
import ru.rustore.sdk.pushexample.notifications.PushLogger
import ru.rustore.sdk.pushexample.notifications.wrapper.NotificationManagerWrapper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initPushes()
    }

    /*
        Инициализируем SDK и создаем канал для отображения пуш-уведомлений средствами SDK.
     */
    private fun initPushes() {
        createNotificationPushChannel()
        RuStorePushClient.init(
            application = this,
            projectId = BuildConfig.PUSH_PROJECT_ID,
            logger = PushLogger(tag = "PushExampleLogger")
        )
        RuStorePushClient.getToken().addOnCompleteListener(object : OnCompleteListener<String>{
            override fun onFailure(throwable: Throwable) {
                Log.e(LOG_TAG, "getToken onFailure", throwable)
            }

            override fun onSuccess(result: String) {
                Log.d(LOG_TAG, "getToken onSuccess token = $result")
            }
        })
    }

    /*
        Создаем канал для отображения пуш-уведомлений средствами SDK.
        channelId указанный в этом методе должен совпадать с тем, который указан в манифесте приложения
     */
    private fun createNotificationPushChannel() {
        NotificationManagerWrapper.getInstance(this).createNotificationChannel(
            channelId = getString(R.string.notifications_notification_push_channel_id),
            channelName = getString(R.string.notifications_notification_push_channel_name)
        )
    }

    companion object {
        private const val LOG_TAG = "App"
    }
}