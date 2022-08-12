package com.chlqudco.develop.byeongchaetalk.presentation.chatlist

import com.chlqudco.develop.byeongchaetalk.databinding.FragmentChatListBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseFragment
import org.koin.android.ext.android.inject

internal class ChatListFragment : BaseFragment<ChatListViewModel,FragmentChatListBinding >() {

    override val viewModel by inject<ChatListViewModel>()

    override fun getViewBinding() = FragmentChatListBinding.inflate(layoutInflater)

}