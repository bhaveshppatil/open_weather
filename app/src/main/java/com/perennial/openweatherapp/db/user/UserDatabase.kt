package com.perennial.openweatherapp.db.user

import androidx.room.Database
import androidx.room.RoomDatabase
import com.perennial.openweatherapp.db.weather.WeatherModel

@Database(entities = [User::class, WeatherModel::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}