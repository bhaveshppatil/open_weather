package com.perennial.openweatherapp.db.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather History")
data class WeatherModel(
    @ColumnInfo val latitude: String,
    @ColumnInfo val longitude: String,
    @ColumnInfo val pressure: Int,
    @ColumnInfo val sunrise: String,
    @ColumnInfo val sunset: String,
    @ColumnInfo val temp: String,
    @ColumnInfo val imageUrl: String,
    @ColumnInfo val address: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null
}