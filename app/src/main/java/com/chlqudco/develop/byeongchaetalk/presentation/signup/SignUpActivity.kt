package com.chlqudco.develop.byeongchaetalk.presentation.signup

import android.os.Bundle
import com.chlqudco.develop.byeongchaetalk.databinding.ActivitySignUpBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseActivity
import org.koin.android.ext.android.inject

internal class SignUpActivity : BaseActivity<SignUpViewModel, ActivitySignUpBinding>() {

    override val viewModel by inject<SignUpViewModel>()

    override fun getViewBinding() = ActivitySignUpBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}