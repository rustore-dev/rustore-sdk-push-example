# Пример внедрения RuStore Push SDK

### [Документация RuStore Push SDK](https://help.rustore.ru/rustore/for_developers/developer-documentation/RuStore-SDK-push-notifications)

## Условия работы SDK

- Инициализировать библиотеку и передать в нее ID проекта пуш-уведомлений из консоли разработчика. Инициализацию библиотеки рекомендуется проводить в Application классе вашего приложения.

```kotlin
RuStorePushClient.init(
    application = this,
    projectId = "32FcaDWjM03DrgLJLoY5PRLT5GaahRBb", // Необходимо заменить на свой ID проекта пуш-уведомлений из консоли
    logger = PushLogger(tag = "PushExampleLogger")
)
```

- ApplicationId, указанный в build.gradle, совпадает с applicationId apk-файла, который вы публиковали в консоль RuStore. Также должна совпадать подпись приложения.

```kotlin
android {
    defaultConfig {
        applicationId = "ru.rustore.sdk.pushexample" // Зачастую в buildTypes приписывается .debug
    }
}
```

## Получение пуш-уведомлений

Все пуш-уведомления вы можете получить реализовав сервис наследник `RuStoreMessagingService` в своем приложении. Важно переопределить метод `onMessageReceived`, в который будут приходить новые пуш-уведомления.

Пример реализации сервиса:
```kotlin
class PushListenerService : RuStoreMessagingService() {

    override fun onNewToken(token: String) {
        // Получение нового пуш-токена
    }

    override fun onMessageReceived(message: RemoteMessage) {
        // Обработка полученного пуш-уведомления
    }

    override fun onError(errors: List<RuStorePushClientException>) {
        // Получение ошибок, которые могут возникнуть во время работы SDK
        errors.forEach { error -> error.printStackTrace() }
    }
}
```

## Отображение пуш-уведомлений

- SDK способна отобразить пуш-уведомление, у которого заполены поля объекта `Notification`. Главное, чтобы было заполнено поле `title`.
- SDK не отображает и не использует контент переданный в поле `data`. Обрабатывать пуш-уведомления, которые содержат такое поле необходимо самостоятельно. Пример обработки можно найти в `PushListenerService`.
- Вы можете указать свой канал для отображения пуш-уведомлений в манифесте приложения. Однако, такой канал вы должны предварительно создавать самостоятельно, иначе пуш-уведомления отображаться не будут. Канал является необязательным параметром.
```xml
<meta-data
            android:name="ru.rustore.sdk.pushclient.default_notification_channel_id"
            android:value="@string/notifications_notification_push_channel_id" />
```
- Вы также можете настроить иконку и цвет уведомления по умолчанию, указав соответствующие параметры в манифесте приложения. Данные параметры являются необязательными.
```xml
<meta-data
    android:name="ru.rustore.sdk.pushclient.default_notification_icon"
    android:resource="@drawable/ic_baseline_android_24" />
<meta-data
    android:name="ru.rustore.sdk.pushclient.default_notification_color"
    android:resource="@color/your_favorite_color" />
```

## Есть вопросы
Если появились вопросы по интеграции SDK пуш-уведомлений, обратитесь по этой ссылке: https://help.rustore.ru/rustore/trouble/user/help_user_email или напишите на почту support@rustore.ru.