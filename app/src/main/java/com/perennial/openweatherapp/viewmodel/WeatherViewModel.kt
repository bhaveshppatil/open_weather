package com.perennial.openweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.perennial.openweatherapp.db.weather.WeatherModel
import com.perennial.openweatherapp.remote.model.WeatherResponse
import com.perennial.openweatherapp.repository.WeatherRepository
import com.perennial.openweatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    fun getDataFromApi(
        lat: Double,
        lon: Double,
        api_key: String
    ): LiveData<Resource<WeatherResponse>> {
        return liveData(Dispatchers.IO) {
            val data = weatherRepository.getWeatherDataFromApi(lat, lon, api_key)
            data.data?.let {
                val mainTemp: String =
                    String.format("%.0f", (it.main.temp + 0.01) - 273.15).toString()
                val minTemp: Double =
                    String.format("%.2f", it.main.temp_min - 273.15).toDouble()
                val maxTemp: Double =
                    String.format("%.2f", it.main.temp_max - 273.15).toDouble()
                val latitude = "${it.coord.lat} E"
                val longitude = "${it.coord.lon} N"
                it.main.temp_min = minTemp
                it.main.temp_max = maxTemp
                it.main.temp = mainTemp.toDouble()
                it.coord.lat = latitude
                it.coord.lon = longitude
            }
            emit(data)
        }
    }

    fun addWeatherHistoryToRoom(weatherModel: WeatherModel) {
        viewModelScope.launch {
            weatherRepository.addWeatherHistory(weatherModel)
        }
    }

    fun getWeatherHistory(): LiveData<List<WeatherModel>> {
        return weatherRepository.getWeatherHistory()
    }

}