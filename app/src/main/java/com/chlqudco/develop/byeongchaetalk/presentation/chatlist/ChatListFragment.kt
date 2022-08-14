package com.chlqudco.develop.byeongchaetalk.presentation.chatlist

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.CHILD_CHAT
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_CHATS
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_USERS
import com.chlqudco.develop.byeongchaetalk.databinding.FragmentChatListBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.ChatListModel
import com.chlqudco.develop.byeongchaetalk.presentation.adapter.ChatListAdapter
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseFragment
import com.chlqudco.develop.byeongchaetalk.presentation.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

internal class ChatListFragment : BaseFragment<ChatListViewModel, FragmentChatListBinding>() {

    private val auth by lazy { Firebase.auth }
    private lateinit var chatDB: DatabaseReference
    private lateinit var adapter: ChatListAdapter
    private lateinit var userDB: DatabaseReference

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

        //DB 초기화
        userDB = Firebase.database.reference.child(DB_USERS)

        //리사이클러 뷰 초기화
        adapter = ChatListAdapter(
            { chatListModel ->
            Toast.makeText(context, "${chatListModel.name}과의 채팅방", Toast.LENGTH_SHORT).show()
        },
            { chatListModel ->
                AlertDialog.Builder(context)
                    .setTitle("나가기")
                    .setMessage("정말 나가시겠습니까?")
                    .setPositiveButton("네") { _, _ ->
                        //채팅방 삭제하기
                        removeChatRoom(chatListModel)
                    }
                    .setNegativeButton("아니오") { _, _ -> }
                    .create()
                    .show()
        })
        binding.chatListRecyclerView.adapter = adapter
        binding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        //채팅 목록 불러오기
        chatDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid).child(CHILD_CHAT)
        getChatList()
    }

    private fun getChatList() {
        chatDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //일단 비우기
                chatList.clear()

                snapshot.children.forEach {
                    val model = it.getValue(ChatListModel::class.java)

                    model ?: return

                    chatList.add(model)
                }

                binding.chatListMainTextView.isVisible = chatList.isEmpty()

                adapter.setChatList(chatList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun removeChatRoom(model : ChatListModel) {
        //내 DB 삭제
        userDB.child(auth.currentUser!!.uid).child(CHILD_CHAT).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val youModel = it.getValue(ChatListModel::class.java)

                    youModel ?: return

                    if (youModel.uid == model.uid ){
                        it.ref.removeValue().addOnCompleteListener { task ->
                            this@ChatListFragment.getChatList()
                        }
                        return@forEach
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })


        //상대방 DB 삭제
        userDB.child(model.uid).child(CHILD_CHAT).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val youModel = it.getValue(ChatListModel::class.java)

                    youModel ?: return

                    if (youModel.uid == auth.currentUser!!.uid ){
                        it.ref.removeValue().addOnCompleteListener { task ->
                            this@ChatListFragment.getChatList()
                        }
                        return@forEach
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

    }

}