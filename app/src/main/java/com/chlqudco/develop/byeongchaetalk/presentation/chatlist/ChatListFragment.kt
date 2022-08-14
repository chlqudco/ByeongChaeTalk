package com.chlqudco.develop.byeongchaetalk.presentation.chatlist

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.CHILD_CHAT
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_USERS
import com.chlqudco.develop.byeongchaetalk.databinding.FragmentChatListBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.ChatListModel
import com.chlqudco.develop.byeongchaetalk.presentation.adapter.ChatListAdapter
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseFragment
import com.chlqudco.develop.byeongchaetalk.presentation.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

internal class ChatListFragment : BaseFragment<ChatListViewModel,FragmentChatListBinding >() {

    private val auth by lazy { Firebase.auth }
    private lateinit var chatDB: DatabaseReference
    private val adapter by lazy { ChatListAdapter() }

    private val chatList = mutableListOf<ChatListModel>()

    override val viewModel by inject<ChatListViewModel>()

    override fun getViewBinding() = FragmentChatListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        //이름 바꾸기
        (activity as MainActivity).setTopTextViewText("채팅 목록")

        //리사이클러 뷰 초기화
        binding.chatListRecyclerView.adapter = adapter
        binding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        //채팅 목록 불러오기
        val chatDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid).child(CHILD_CHAT)
        chatDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val model = it.getValue(ChatListModel::class.java)
                    model ?: return

                    chatList.add(model)
                }

                adapter.setChatList(chatList)

            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getChatList() {
        chatDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

}