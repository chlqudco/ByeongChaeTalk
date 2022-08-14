package com.chlqudco.develop.byeongchaetalk.di

import com.chlqudco.develop.byeongchaetalk.presentation.chat.ChatViewModel
import com.chlqudco.develop.byeongchaetalk.presentation.chatlist.ChatListViewModel
import com.chlqudco.develop.byeongchaetalk.presentation.login.LogInViewModel
import com.chlqudco.develop.byeongchaetalk.presentation.main.MainViewModel
import com.chlqudco.develop.byeongchaetalk.presentation.signup.SignUpViewModel
import com.chlqudco.develop.byeongchaetalk.presentation.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //viewModel
    viewModel { MainViewModel() }
    viewModel { LogInViewModel() }
    viewModel { SignUpViewModel() }
    viewModel { ChatListViewModel() }
    viewModel { UserViewModel() }
    viewModel { ChatViewModel() }

}