package com.perennial.openweatherapp.utils

interface StateListener {

    fun onLoading()

    fun onSuccess(message:String?)

    fun onError(message: String)

}