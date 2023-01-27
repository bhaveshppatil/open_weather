package com.perennial.openweatherapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.databinding.ActivityLoginBinding
import com.perennial.openweatherapp.utils.StateListener
import com.perennial.openweatherapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), StateListener {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        viewModel.stateListener = this

        binding.tvNewUser.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLoginUp.setOnClickListener {
            viewModel.loginUser()
        }

    }

    override fun onLoading() {}

    override fun onSuccess(message: String?) {
        showToast(this, message!!)
        printLog(message)
        startActivity(Intent(this, WeatherActivity::class.java))
    }

    override fun onError(message: String) {
        showToast(this, message)
        printLog(message)
    }


    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun printLog(message: String) {
        Log.d("openweather", message)
    }
}