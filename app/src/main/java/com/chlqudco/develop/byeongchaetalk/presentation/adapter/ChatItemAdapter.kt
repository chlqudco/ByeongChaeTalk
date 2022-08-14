package com.chlqudco.develop.byeongchaetalk.presentation.adapter

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chlqudco.develop.byeongchaetalk.databinding.ItemChatBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.ChatModel

class ChatItemAdapter: RecyclerView.Adapter<ChatItemAdapter.ViewHolder>() {

    var chatList = mutableListOf<ChatModel>()

    inner class ViewHolder(val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chatModel: ChatModel){
            binding.chatMessageTextView.text = chatModel.message

            if (chatModel.isMe){
                binding.chatMessageTextView.gravity = Gravity.END
            } else {
                binding.chatMessageTextView.gravity = Gravity.START
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int = chatList.size

}