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
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    fun getDataFromApi(city: String, apiKey: String): LiveData<Resource<WeatherResponse>> {
        return liveData(Dispatchers.IO) {
            val data = weatherRepository.getWeatherDataFromApi(city, apiKey)
            emit(data)
        }
    }

    fun addWeatherHistoryToRoom(weatherModel: WeatherModel){
        viewModelScope.launch {
            weatherRepository.addWeatherHistory(weatherModel)
        }
    }

    fun getWeatherHistory() : LiveData<List<WeatherModel>>{
        return weatherRepository.getWeatherHistory()
    }

}