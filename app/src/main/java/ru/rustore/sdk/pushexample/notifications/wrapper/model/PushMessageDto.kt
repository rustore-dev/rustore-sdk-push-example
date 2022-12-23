package ru.rustore.sdk.pushexample.notifications.wrapper.model

import kotlinx.serialization.Serializable

@Serializable
data class PushMessageDto(
    val title: String,
    val message: String
)
