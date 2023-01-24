package com.perennial.openweatherapp.repository

import androidx.lifecycle.LiveData
import com.perennial.openweatherapp.db.UserDatabase
import com.perennial.openweatherapp.db.weather.WeatherModel
import com.perennial.openweatherapp.remote.ApiService
import com.perennial.openweatherapp.remote.model.WeatherResponse
import com.perennial.openweatherapp.utils.Resource
import com.perennial.openweatherapp.utils.ResponseHandler
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService, private val database: UserDatabase) {

    private val responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getWeatherDataFromApi(city: String, apikey: String): Resource<WeatherResponse> {
        return try {
            val weatherResponse: WeatherResponse =
                apiService.getWeatherData(city = city, api_key = apikey)
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