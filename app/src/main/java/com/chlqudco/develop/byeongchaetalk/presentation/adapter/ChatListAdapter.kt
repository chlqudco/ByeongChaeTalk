package com.chlqudco.develop.byeongchaetalk.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chlqudco.develop.byeongchaetalk.databinding.ItemChatListBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.ChatListModel

class ChatListAdapter(val clickListener: (ChatListModel) -> Unit, val exitButtonClickedListener: (ChatListModel) -> Unit): RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    private var chatList: List<ChatListModel> = listOf()

    inner class ViewHolder(private val binding: ItemChatListBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(chatListModel: ChatListModel){
            binding.chatListNameTextView.text = "${chatListModel.name}님과의 채팅방"

            binding.root.setOnClickListener {
                clickListener(chatListModel)
            }

            binding.chatListExitButton.setOnClickListener {
                exitButtonClickedListener(chatListModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun setChatList(chatList: List<ChatListModel>){
        this.chatList = chatList
        notifyDataSetChanged()
    }
}