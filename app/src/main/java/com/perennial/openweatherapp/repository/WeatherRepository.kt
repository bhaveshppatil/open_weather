package com.perennial.openweatherapp.repository

import androidx.lifecycle.LiveData
import com.perennial.openweatherapp.db.user.UserDatabase
import com.perennial.openweatherapp.db.weather.WeatherDatabase
import com.perennial.openweatherapp.db.weather.WeatherModel
import com.perennial.openweatherapp.remote.ApiService
import com.perennial.openweatherapp.remote.model.WeatherResponse
import com.perennial.openweatherapp.utils.Resource
import com.perennial.openweatherapp.utils.ResponseHandler
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService, private val database: WeatherDatabase) {

    private val responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getWeatherDataFromApi(lat: Double, lon: Double, api_key : String): Resource<WeatherResponse> {
        return try {
            val weatherResponse: WeatherResponse =
                apiService.getWeatherData(lat, lon, api_key)
            responseHandler.handleSuccess(weatherResponse)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun addWeatherHistory(weatherModel: WeatherModel) = database.weatherDao().insertWeatherHistory(weatherModel)

    fun getWeatherHistory() : LiveData<List<WeatherModel>> {
        return database.weatherDao().getWeatherHistoryData()
    }

}