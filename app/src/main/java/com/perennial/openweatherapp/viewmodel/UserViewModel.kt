package com.perennial.openweatherapp.viewmodel

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.perennial.openweatherapp.db.User
import com.perennial.openweatherapp.repository.UserRepository
import com.perennial.openweatherapp.utils.Constants.AUTH_DATASTORE_KEY
import com.perennial.openweatherapp.utils.StateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val authRepository: UserRepository) :
    ViewModel(), Observable {

    var stateListener: StateListener? = null

    @Bindable
    val firstName = MutableLiveData<String>()

    @Bindable
    val emailAddress = MutableLiveData<String>()

    @Bindable
    val phoneNumber = MutableLiveData<String>()

    @Bindable
    val password = MutableLiveData<String>()

    fun loginUser() {
        stateListener?.onLoading()

        if (emailAddress.value.isNullOrEmpty()) {
            stateListener?.onError("Enter email address")
            return
        } else if (password.value.isNullOrEmpty()) {
            stateListener?.onError("Enter password")
            return
        }


        viewModelScope.launch {
            try {
                val loginResponse = authRepository.loginUser(emailAddress.value!!, password.value!!)

                loginResponse.collect { user ->
                    if (user == null) {
                        stateListener?.onError("User account not found")
                        return@collect
                    } else {
                        stateListener?.onSuccess("Welcome, ${user.firstName}")

                        authRepository.setUserLoggedIn(AUTH_DATASTORE_KEY, true)

                        return@collect
                    }
                }
            } catch (e: Exception) {
                stateListener?.onError(e.message!!)
                return@launch
            }
        }
    }

    fun registerUser() {
        when {
            firstName.value.isNullOrEmpty() -> {
                stateListener?.onError("Enter first name")
                return
            }
            emailAddress.value.isNullOrEmpty() -> {
                stateListener?.onError("Enter email address")
                return
            }
            phoneNumber.value.isNullOrEmpty() -> {
                stateListener?.onError("Enter phone number")
                return
            }
            password.value.isNullOrEmpty() -> {
                stateListener?.onError("Enter password")
                return
            }
        }

        viewModelScope.launch {
            try {
                val user = User(
                    0,
                    firstName.value!!,
                    emailAddress.value!!,
                    phoneNumber.value!!,
                    password.value!!
                )
                authRepository.registerUser(user)
                stateListener?.onSuccess("Welcome, ${firstName.value}")
                authRepository.setUserLoggedIn(AUTH_DATASTORE_KEY, true)

                return@launch
            } catch (e: Exception) {
                stateListener?.onError(e.message!!)
                return@launch
            }
        }
    }


    fun isUserLoggedIn() = liveData {
        val isUserLoggedIn = authRepository.isUserLoggedIn(AUTH_DATASTORE_KEY, false)
        Log.e("isUserLoggedIn", "isUserLoggedIn viewModel: isUserLoggedIn: $isUserLoggedIn")
        emit(isUserLoggedIn)
        return@liveData
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}