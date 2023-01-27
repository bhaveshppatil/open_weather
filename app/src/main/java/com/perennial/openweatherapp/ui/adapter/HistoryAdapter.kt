package com.perennial.openweatherapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.db.weather.WeatherModel
import com.perennial.openweatherapp.ui.viewholder.HistoryViewHolder

class HistoryAdapter(
    val context: Context,
    val weatherHistoryList: MutableList<WeatherModel>
    ) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.history_item_layout, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val weatherModel = weatherHistoryList[position]
        holder.bindData(weatherModel)
    }

    override fun getItemCount(): Int {
        return weatherHistoryList.size
    }
}