package com.perennial.openweatherapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

object Constants {

    const val AUTH_DATASTORE_NAME = "User DataStore"
    const val AUTH_DATASTORE_KEY = "User DataStore Key"
    const val BASE_URL = "https://api.openweathermap.org/"
    const val ENDPOINT = "data/2.5/weather"
    const val API_KEY = "92f4e9a9c233be99f0b33d1c58c72386"

        //https://api.openweathermap.org/data/2.5/weather?q=Mumbai&appid=92f4e9a9c233be99f0b33d1c58c72386

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun printLog(message: String) {
        Log.d("OpenWeatherLogs", message)
    }

}
