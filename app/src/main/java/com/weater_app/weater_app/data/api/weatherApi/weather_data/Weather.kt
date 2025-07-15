package com.weater_app.weater_app.data.api.weatherApi.weather_data

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)