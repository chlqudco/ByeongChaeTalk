package com.chlqudco.develop.byeongchaetalk.domain.model

data class ChatModel(
    val senderId: String,
    val senderName: String,
    val message: String,
    var isMe: Boolean
) {
    constructor(): this("","","", true)
}