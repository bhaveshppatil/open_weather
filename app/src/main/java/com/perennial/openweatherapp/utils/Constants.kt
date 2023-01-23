package com.perennial.openweatherapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

object Constants {

    const val AUTH_DATASTORE_NAME = "User DataStore"
    const val AUTH_DATASTORE_KEY = "User DataStore Key"

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun printLog(message: String) {
        Log.d("OpenWeatherLogs", message)
    }

}
