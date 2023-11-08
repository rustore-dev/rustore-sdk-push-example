package ru.rustore.sdk.pushexample.notifications.wrapper.model

data class AppNotification(
    val id: Int,
    val title: String?,
    val message: String?,
    val channelId: String?,
    val channelName: String?
)
