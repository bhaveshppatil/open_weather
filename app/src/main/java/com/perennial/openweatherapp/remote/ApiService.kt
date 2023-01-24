package com.perennial.openweatherapp.remote

import com.perennial.openweatherapp.remote.model.WeatherResponse
import com.perennial.openweatherapp.utils.Constants.ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//https://api.openweathermap.org/data/2.5/weather?q=Mumbai&appid=92f4e9a9c233be99f0b33d1c58c72386

    @GET(ENDPOINT)
    suspend fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") api_key : String
    ) : WeatherResponse
}