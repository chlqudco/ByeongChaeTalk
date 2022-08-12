package com.chlqudco.develop.byeongchaetalk.presentation.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_NAME
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_USERS
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_USER_ID
import com.chlqudco.develop.byeongchaetalk.databinding.FragmentUserBinding
import com.chlqudco.develop.byeongchaetalk.domain.model.UserModel
import com.chlqudco.develop.byeongchaetalk.presentation.adapter.UserListAdapter
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseFragment
import com.chlqudco.develop.byeongchaetalk.presentation.main.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

internal class UserFragment : BaseFragment<UserViewModel, FragmentUserBinding>() {

    private val auth by lazy { Firebase.auth }
    private lateinit var userDB: DatabaseReference
    private val adapter by lazy { UserListAdapter() }

    private val userList = mutableListOf<UserModel>()

    override val viewModel by inject<UserViewModel>()

    override fun getViewBinding(): FragmentUserBinding = FragmentUserBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        //이름 바꾸기
        (activity as MainActivity).setTopTextViewText("유저 목록")

        //리사이클러뷰 초기화
        binding.userRecyclerView.adapter = adapter
        binding.userRecyclerView.layoutManager = LinearLayoutManager(context)

        //유저 목록 불러오기
        userDB = Firebase.database.reference.child(DB_USERS)
        val currentUserDB = userDB.child(getCurrentUserID())
        currentUserDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) { getUserList() }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getUserList() {
        userDB.addChildEventListener(object : ChildEventListener{
            // 새로운 회원이 가입하면 이 함수가 호출된다
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //snapshot은 새로운 회원을 가리키는 DB 이다
                //따라서 snapshot의 유저ID가 나와 같지 않다면 목록에 추가해주면 되는 것이다
                if (snapshot.child(DB_USER_ID).value != getCurrentUserID()){
                    //정보를 불러온 뒤 모델을 하나 만든다
                    val userId = snapshot.child(DB_USER_ID).value.toString()
                    val name = snapshot.child(DB_NAME).value.toString()
                    val userModel = UserModel(userId, name)

                    // 리스트에 없는 유저인 경우에만 집어 넣는다
                    if (userList.contains(userModel).not()){
                        userList.add(userModel)
                    }
                    // 사전 순으로 정렬한 뒤
                    userList.sortBy { it.name }

                    //어댑터에 넣어준다
                    adapter.setUserList(userList)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    //유저 아이디 가져오는 함수
    private fun getCurrentUserID(): String {
        //어쩌다보니 로그인이 풀린 상황이 있는 경우
        if (auth.currentUser == null) {
            Toast.makeText(context, "로그인이 되어있지않습니다.", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).goLogIn()
        }

        return auth.currentUser!!.uid
    }

}