package com.perennial.openweatherapp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class WeatherApp: Application(){

    override fun onCreate() {
        super.onCreate()
    }
}