package com.perennial.openweatherapp.remote.model

data class Sys(
    val country: String,
    val id: Int,
    var sunrise: Int,
    var sunset: Int,
    val type: Int
)