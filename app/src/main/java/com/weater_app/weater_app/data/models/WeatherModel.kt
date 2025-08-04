package com.weater_app.weater_app.data.models

import com.weater_app.weater_app.data.api.weatherApi.weather_data.City
import com.weater_app.weater_app.data.api.weatherApi.weather_data.Item0

data class WeatherModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item0>,
    val message: Int,
)