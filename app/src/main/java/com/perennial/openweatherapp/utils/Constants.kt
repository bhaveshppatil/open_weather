package com.perennial.openweatherapp.utils

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.utils.DialogUtil.showAlertDialog

object Constants {

    const val AUTH_DATASTORE_NAME = "User DataStore"
    const val AUTH_DATASTORE_KEY = "User DataStore Key"
    const val GPS_REQUEST_CODE = 99
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

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    fun isGpsEnable(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val enableLocation = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (enableLocation) {
            return true
        } else {
            if (context is Activity) {
                showAlertDialog(context, context.getString(R.string.enable_gps_service))
            }
        }
        return false
    }


    fun checkConnection(context: Context): Boolean {
        if (isOnline(context)) {
            return true
        } else {
            if (context is Activity) {
                showAlertDialog(
                    context,
                    context.getString(R.string.check_internet_connection)
                )
            }
        }
        return false
    }


}
