package com.perennial.openweatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.databinding.ActivityMainBinding
import com.perennial.openweatherapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            initUI()
        }, 2000)
    }

    private fun initUI() {
        viewModel.isUserLoggedIn().observe(this) { loggedIn ->
            Log.d("isUserLoggedIn", loggedIn.toString())
            if (loggedIn) {
                startActivity(Intent(this, WeatherActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }
    }
}