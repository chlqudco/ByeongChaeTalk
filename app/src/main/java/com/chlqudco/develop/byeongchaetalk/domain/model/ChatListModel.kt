package com.chlqudco.develop.byeongchaetalk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatListModel(
    val uid: String,
    val name : String,
    val key: Long
): Parcelable {
    constructor(): this("","",0)
}