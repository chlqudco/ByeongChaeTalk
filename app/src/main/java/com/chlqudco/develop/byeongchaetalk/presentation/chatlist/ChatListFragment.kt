package com.chlqudco.develop.byeongchaetalk.presentation.chatlist

import android.os.Bundle
import android.view.View
import com.chlqudco.develop.byeongchaetalk.databinding.FragmentChatListBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseFragment
import com.chlqudco.develop.byeongchaetalk.presentation.main.MainActivity
import org.koin.android.ext.android.inject

internal class ChatListFragment : BaseFragment<ChatListViewModel,FragmentChatListBinding >() {

    override val viewModel by inject<ChatListViewModel>()

    override fun getViewBinding() = FragmentChatListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        //이름 바꾸기
        (activity as MainActivity).setTopTextViewText("채팅 목록")

        //채팅 목록 불러오기
    }

}