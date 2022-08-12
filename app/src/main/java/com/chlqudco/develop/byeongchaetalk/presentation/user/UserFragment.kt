package com.chlqudco.develop.byeongchaetalk.presentation.user

import com.chlqudco.develop.byeongchaetalk.databinding.FragmentUserBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseFragment
import org.koin.android.ext.android.inject

internal class UserFragment : BaseFragment<UserViewModel, FragmentUserBinding>() {

    override val viewModel by inject<UserViewModel>()

    override fun getViewBinding(): FragmentUserBinding = FragmentUserBinding.inflate(layoutInflater)

}