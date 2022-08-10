package com.chlqudco.develop.byeongchaetalk.presentation.signup

import androidx.lifecycle.viewModelScope
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class SignUpViewModel: BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {

    }

}