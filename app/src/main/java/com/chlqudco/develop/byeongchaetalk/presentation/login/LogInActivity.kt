package com.chlqudco.develop.byeongchaetalk.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chlqudco.develop.byeongchaetalk.R
import com.chlqudco.develop.byeongchaetalk.databinding.ActivityLogInBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseActivity
import com.chlqudco.develop.byeongchaetalk.presentation.signup.SignUpActivity
import org.koin.android.ext.android.inject

internal class LogInActivity : BaseActivity<LogInViewModel, ActivityLogInBinding>() {

    override val viewModel by inject<LogInViewModel>()

    override fun getViewBinding() = ActivityLogInBinding.inflate(layoutInflater)

    override fun observeData() { }

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

    }

}