package com.perennial.openweatherapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.databinding.ActivityRegisterBinding
import com.perennial.openweatherapp.utils.Constants
import com.perennial.openweatherapp.utils.Constants.printLog
import com.perennial.openweatherapp.utils.Constants.showToast
import com.perennial.openweatherapp.utils.CustomMultiColorProgressBar
import com.perennial.openweatherapp.utils.StateListener
import com.perennial.openweatherapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), StateListener {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var progressBar: CustomMultiColorProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        progressBar =
            CustomMultiColorProgressBar(this, "Please wait...\nWe're running your request")
        binding.viewModel = viewModel
        viewModel.stateListener = this

        binding.tvHaveAnAccountUp.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignup.setOnClickListener {
            viewModel.registerUser()
        }
    }

    override fun onLoading() {
        progressBar.showProgressBar()
    }

    override fun onSuccess(message: String?) {
        progressBar.hideProgressBar()
        showToast(this, message!!)
        printLog(message)
        startActivity(Intent(this, WeatherActivity::class.java))
        finish()

    }

    override fun onError(message: String) {
        progressBar.hideProgressBar()
        showToast(this, message!!)
        printLog(message)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}