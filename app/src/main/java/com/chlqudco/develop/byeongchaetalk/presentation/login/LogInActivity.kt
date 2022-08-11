package com.chlqudco.develop.byeongchaetalk.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.chlqudco.develop.byeongchaetalk.R
import com.chlqudco.develop.byeongchaetalk.databinding.ActivityLogInBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseActivity
import com.chlqudco.develop.byeongchaetalk.presentation.main.MainActivity
import com.chlqudco.develop.byeongchaetalk.presentation.signup.SignUpActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

internal class LogInActivity : BaseActivity<LogInViewModel, ActivityLogInBinding>() {

    override val viewModel by inject<LogInViewModel>()

    override fun getViewBinding() = ActivityLogInBinding.inflate(layoutInflater)

    override fun observeData() { }

    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {

        //회원가입 창 이동
        binding.LogInSignUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        //로그인 요청
        binding.LogInSignInButton.setOnClickListener{
            requestLogIn()
        }

    }

    private fun requestLogIn() {
        val userEmail = binding.LogInIdEditText.text.toString()
        val password = binding.LogInPasswordEditText.text.toString()

        //예외처리 1. 아이디나 비밀번호를 입력하지 않은 경우
        if (userEmail.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "아직 다 입력하지 않았습니다.",Toast.LENGTH_SHORT).show()
            return
        }

        //예외처리 2. 이메일 형식이 아닌 경우
        if (userEmail.contains('@').not()){
            Toast.makeText(this, "아이디가 이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show()
            return
        }

        //로그인 요청
        auth.signInWithEmailAndPassword(userEmail,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "로그인 되었습니다",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "없는 ID이거나 오류가 발생했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
    }

}