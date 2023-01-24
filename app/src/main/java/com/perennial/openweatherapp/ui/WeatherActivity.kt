package com.perennial.openweatherapp.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.databinding.ActivityWeatherBinding
import com.perennial.openweatherapp.db.weather.WeatherModel
import com.perennial.openweatherapp.remote.model.WeatherResponse
import com.perennial.openweatherapp.utils.Constants.API_KEY
import com.perennial.openweatherapp.utils.Constants.showToast
import com.perennial.openweatherapp.utils.Status
import com.perennial.openweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityWeatherBinding
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        binding.mainContainer.visibility = View.GONE

        binding.searchBarTemp.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadWeatherFromApi(query!!)
                binding.searchBarTemp.setQuery(null, false)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun loadWeatherFromApi(city: String) {
        binding.loader.visibility = View.VISIBLE
        weatherViewModel.getDataFromApi(city, API_KEY).observe(this, Observer {
            when (it.status) {
                Status.ERROR -> {
                    binding.loader.visibility = View.GONE
                    showToast(this, "Network error")
                    binding.errorText.text = it.message
                }
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    binding.loader.visibility = View.GONE
                    binding.mainContainer.visibility = View.VISIBLE
                    updateUI(it.data)
                }
            }
        })
    }

    private fun updateUI(response: WeatherResponse?) {
        response?.let { it ->
            val mainTemp: String = String.format("%.0f", (it.main.temp + 0.01) - 273.15).toString()
            val minTemp: Double =
                String.format("%.2f", it.main.temp_min - 273.15).toDouble()
            val maxTemp: Double =
                String.format("%.2f", it.main.temp_max - 273.15).toDouble()
            val latitude = "${it.coord.lat} E"
            val longitude = "${it.coord.lon} N"
            val sunrise =
                SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunrise * 1000L))
            val sunset =
                SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunset * 1000L))
            val address = "${it.sys.country} , ${it.name}"

            binding.address.text = address
            val date =
                SimpleDateFormat(
                    "dd/MM/yyyy hh:mm a",
                    Locale.ENGLISH
                ).format((Date(it.dt * 1000L)));
            binding.updatedAt.text = date
            it.weather.forEach {
                imageUrl = "http://openweathermap.org/img/wn/${it.icon}@4x.png"
                binding.status.text = it.description
                Glide.with(this@WeatherActivity)
                    .load(imageUrl).into(binding.weatherPNG)
            }
            binding.temp.text = "$mainTemp°C"
            binding.tempMin.text = "Min Temp: $minTemp°C"
            binding.tempMax.text = "Max Temp: $maxTemp°C"
            binding.sunrise.text = sunrise
            binding.sunset.text = sunset
            binding.wind.text = "${it.wind.speed}"
            binding.pressure.text = "${it.main.pressure}"

            binding.latitude.text = latitude
            binding.longitude.text = longitude

            val weatherModel = WeatherModel(
                latitude = latitude,
                longitude = longitude,
                pressure = it.main.pressure,
                sunrise = sunrise,
                sunset = sunset,
                temp = "$mainTemp°C",
                imageUrl = imageUrl,
                address = address
            )

            weatherViewModel.addWeatherHistoryToRoom(weatherModel)
        }
    }
}