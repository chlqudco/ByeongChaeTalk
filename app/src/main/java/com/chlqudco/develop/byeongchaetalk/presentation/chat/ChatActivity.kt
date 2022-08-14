package com.chlqudco.develop.byeongchaetalk.presentation.chat

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_CHATS
import com.chlqudco.develop.byeongchaetalk.databinding.ActivityChatBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.ChatListModel
import com.chlqudco.develop.byeongchaetalk.domain.model.ChatModel
import com.chlqudco.develop.byeongchaetalk.presentation.adapter.ChatItemAdapter
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

internal class ChatActivity : BaseActivity<ChatViewModel, ActivityChatBinding>() {

    override val viewModel by inject<ChatViewModel>()

    override fun getViewBinding(): ActivityChatBinding = ActivityChatBinding.inflate(layoutInflater)

    override fun observeData() { }

    private val auth by lazy { Firebase.auth }
    private val chatList = mutableListOf<ChatModel>()
    private val adapter by lazy { ChatItemAdapter() }
    private val userId by lazy { auth.currentUser!!.uid }
    private lateinit var chatDB: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 정보 불러오기
        val chatListModel = intent.getParcelableExtra<ChatListModel>("model")
        binding.chatNameTextView.text = chatListModel!!.name

        // Recycler View 초기화
        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)

        // 여태 했던 채팅들 다 불러오기
        chatDB = Firebase.database.reference.child(DB_CHATS).child("${chatListModel?.key}")
        chatDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatModel::class.java)
                chatItem ?: return

                chatItem.isMe = (chatItem.senderId == userId)

                //?? 뭔가 이상한데
                chatList.add(chatItem)
                adapter.chatList.add(chatItem)
                adapter.notifyDataSetChanged()

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        binding.chatSendButton.setOnClickListener {
            val message = binding.chatMessageEditText.text.toString()

            if (message.isEmpty()) return@setOnClickListener

            val chatModel = ChatModel(
                userId,
                "asd",
                message,
                true
            )

            chatDB
                .push()
                .setValue(chatModel)

            binding.chatMessageEditText.setText("")
        }
    }
}