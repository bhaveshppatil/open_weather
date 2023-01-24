package com.perennial.openweatherapp.db.weather

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherHistory(weatherModel: WeatherModel)

    @Query("SELECT * FROM `Weather History` order by id desc")
    fun getWeatherHistoryData() : LiveData<List<WeatherModel>>

}