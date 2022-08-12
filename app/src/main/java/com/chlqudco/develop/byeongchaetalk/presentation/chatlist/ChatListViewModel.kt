package com.chlqudco.develop.byeongchaetalk.presentation.chatlist

import androidx.lifecycle.viewModelScope
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ChatListViewModel: BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {

    }
}