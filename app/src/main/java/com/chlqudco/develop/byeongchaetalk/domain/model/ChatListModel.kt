package com.chlqudco.develop.byeongchaetalk.domain.model

data class ChatListModel(
    val uid: String,
    val name : String,
    val key: Long
) {
    constructor(): this("","",0)
}