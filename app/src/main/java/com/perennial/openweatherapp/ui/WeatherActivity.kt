package com.perennial.openweatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.databinding.ActivityWeatherBinding
import com.perennial.openweatherapp.db.weather.WeatherModel
import com.perennial.openweatherapp.remote.model.WeatherResponse
import com.perennial.openweatherapp.utils.Constants
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
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var imageUrl = ""

    override fun onStart() {
        super.onStart()
        if (Constants.checkConnection(this) && Constants.isGpsEnable(this)) {
            getLocationData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun loadWeatherFromApi(latitude: Double, longitude: Double) {
        binding.loader.visibility = View.VISIBLE
        weatherViewModel.getDataFromApi(latitude, longitude, API_KEY).observe(this, Observer {
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
            val address = "${it.sys.country} , ${it.name}"
            actionBar?.setTitle(address)
            binding.address.text = address
            binding.updatedAt.text = it.dt.toString()
            val sunrise =
                SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunrise * 1000L))
            val sunset =
                SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(it.sys.sunset * 1000L))
            val date = SimpleDateFormat(
                "dd/MM/yyyy hh:mm a",
                Locale.ENGLISH
            ).format((Date(it.dt * 1000L)));

            it.weather.forEach {
                imageUrl = "http://openweathermap.org/img/wn/${it.icon}@4x.png"
                Log.d("iconImageUrl", imageUrl)
                binding.status.text = it.description
                Glide.with(binding.weatherPNG).load(imageUrl).centerCrop().into(binding.weatherPNG)
            }

            binding.temp.text = "${it.main.temp}°C"
            binding.tempMin.text = "Min Temp: ${it.main.temp_min}°C"
            binding.tempMax.text = "Max Temp: ${it.main.temp_max}°C"
            binding.sunrise.text = sunrise
            binding.sunset.text = sunset
            binding.updatedAt.text = date
            binding.wind.text = "${it.wind.speed}"
            binding.pressure.text = "${it.main.pressure}"
            binding.latitude.text = it.coord.lat
            binding.longitude.text = it.coord.lon

            val weatherModel = WeatherModel(
                latitude = it.coord.lat.toString(),
                longitude = it.coord.lon.toString(),
                pressure = it.main.pressure,
                sunrise = sunrise,
                sunset = sunset,
                temp = "${it.main.temp}°C",
                imageUrl = imageUrl,
                address = address
            )

            weatherViewModel.addWeatherHistoryToRoom(weatherModel)
        }
    }

    private fun getLocationData() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    var address: kotlin.collections.List<Address>? = null
                    try {
                        address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        address?.let {
                            val latitude = it[0].latitude
                            val longitude = it[0].longitude
                            loadWeatherFromApi(latitude, longitude)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            askForPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.GPS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationData()
            } else {
                showToast(this, "Please provide required permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun askForPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            Constants.GPS_REQUEST_CODE
        )
    }
}