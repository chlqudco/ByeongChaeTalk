package com.chlqudco.develop.byeongchaetalk.presentation.signup

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_NAME
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_USERS
import com.chlqudco.develop.byeongchaetalk.data.db.FirebaseDataBaseKey.DB_USER_ID
import com.chlqudco.develop.byeongchaetalk.databinding.ActivitySignUpBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

internal class SignUpActivity : BaseActivity<SignUpViewModel, ActivitySignUpBinding>() {

    private val auth by lazy { Firebase.auth }

    override val viewModel by inject<SignUpViewModel>()

    override fun getViewBinding() = ActivitySignUpBinding.inflate(layoutInflater)

    override fun observeData() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        //회원가입 버튼 구현
        binding.SignUpSignUpButton.setOnClickListener {
            binding.SignUpProgressBar.isVisible = true

            val userEmail = binding.SignUpIdEditText.text.toString()
            val password1 = binding.SignUpPasswordEditText.text.toString()
            val password2 = binding.SignUpPasswordCheckEditText.text.toString()
            val userName = binding.SignUpNameEditText.text.toString()

            // 예외처리 1. 입력 안 한 칸이 있는 경우
            if (userEmail.isEmpty() || password1.isEmpty() || password2.isEmpty()){
                Toast.makeText(this, "입력되지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 예외처리 2. 아이디가 이메일 형식이 아닌 경우
            if (userEmail.contains('@').not()){
                Toast.makeText(this, "아이디는 이메일 형식이여야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 예외처리 3. 비밀번호 두 칸이 서로 다른 경우
            if (password1 != password2){
                Toast.makeText(this, "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 예외처리 4. 비밀번호는 6자 이상이여야 함
            if (password1.length < 6){
                Toast.makeText(this, "비밀번호는 6자 이상이여야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 회원 가입 신청
            auth.createUserWithEmailAndPassword(userEmail, password1)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        // 이름 저장
                        val userId = auth.currentUser!!.uid
                        val currentUserDB = Firebase.database.reference.child(DB_USERS).child(userId)
                        val user = mutableMapOf<String, Any>()
                        user[DB_USER_ID] = userId
                        user[DB_NAME] = userName
                        currentUserDB.updateChildren(user)

                        Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                        binding.SignUpProgressBar.isVisible = false
                        finish()
                    } else{
                        Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                        binding.SignUpProgressBar.isVisible = false
                    }
                }
        }
    }

}