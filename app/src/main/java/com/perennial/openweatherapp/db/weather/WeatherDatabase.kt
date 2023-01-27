package com.perennial.openweatherapp.db.weather

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherModel::class], version = 1, exportSchema = false )
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao() : WeatherDAO
}