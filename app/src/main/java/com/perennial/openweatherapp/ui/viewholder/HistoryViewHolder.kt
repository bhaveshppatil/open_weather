package com.perennial.openweatherapp.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.db.weather.WeatherModel

class HistoryViewHolder(item:View) : RecyclerView.ViewHolder(item) {

    val tvMainHistoryTemp = item.findViewById<TextView>(R.id.tvMainHistoryTemp)
    val ivHistoryIcon = item.findViewById<ImageView>(R.id.ivHistoryIcon)
    val tvLatHistory = item.findViewById<TextView>(R.id.tvLatHistory)
    val tvLonHistory = item.findViewById<TextView>(R.id.tvLonHistory)
    val tvSunriseHistory = item.findViewById<TextView>(R.id.tvSunriseHistory)
    val tvSunsetHistory = item.findViewById<TextView>(R.id.tvSunsetHistory)
    val tvAddressHistory = item.findViewById<TextView>(R.id.tvAddressHistory)

    fun bindData(weatherModel: WeatherModel){
        Glide.with(itemView.context).load(weatherModel.imageUrl).dontAnimate().into(ivHistoryIcon);
        tvMainHistoryTemp.text = weatherModel.temp
        tvLatHistory.text = "Lat- ${weatherModel.latitude}"
        tvLonHistory.text = "Lon- ${weatherModel.longitude}"
        tvSunriseHistory.text  = "Sunrise- ${weatherModel.sunrise}"
        tvSunsetHistory.text = "Sunset- ${weatherModel.sunset}"
        tvAddressHistory.text = weatherModel.address

    }

}