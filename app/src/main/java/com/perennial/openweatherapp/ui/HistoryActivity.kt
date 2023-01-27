package com.perennial.openweatherapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.perennial.openweatherapp.R
import com.perennial.openweatherapp.databinding.ActivityHistoryBinding
import com.perennial.openweatherapp.db.weather.WeatherModel
import com.perennial.openweatherapp.ui.adapter.HistoryAdapter
import com.perennial.openweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val viewModel: WeatherViewModel by viewModels()
    private val historyItemList = mutableListOf<WeatherModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        setRecyclerView()
        binding.progressCircularHistory.visibility = View.VISIBLE

        viewModel.getWeatherHistory().observe(this, Observer {
            historyItemList.clear()
            historyItemList.addAll(it)
            binding.progressCircularHistory.visibility = View.GONE
            updateUI(historyItemList)
            historyAdapter.notifyDataSetChanged()
        })
    }

    private fun updateUI(historyItemList: MutableList<WeatherModel>) {
        if (historyItemList.isEmpty()) {
            binding.apply {
                recyclerView.visibility = View.GONE
                progressCircularHistory.visibility = View.GONE
                tvNoHistoryFound.visibility = View.VISIBLE
                tvNoHistoryFound.text = "No data found"
            }
        } else {
            binding.apply {
                recyclerView.visibility = View.VISIBLE
                tvNoHistoryFound.visibility = View.GONE
            }
        }
    }

    private fun setRecyclerView() {
        historyAdapter = HistoryAdapter(this, historyItemList)
        binding.recyclerView.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
        }
    }
}