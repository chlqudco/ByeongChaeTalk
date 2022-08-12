package com.chlqudco.develop.byeongchaetalk.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.chlqudco.develop.byeongchaetalk.R
import com.chlqudco.develop.byeongchaetalk.databinding.ActivityMainBinding
import com.chlqudco.develop.byeongchaetalk.presentation.base.BaseActivity
import com.chlqudco.develop.byeongchaetalk.presentation.login.LogInActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by inject<MainViewModel>()

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun observeData() {}

    //FireBase Auth
    private val auth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {

        //네비그래프와 바텀네비 연결
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavigationHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.mainBottomNavigationView.setupWithNavController(navController)

        //로그인이 안되어 있으면 로그인 하러 가기
        if (auth.currentUser == null) {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}