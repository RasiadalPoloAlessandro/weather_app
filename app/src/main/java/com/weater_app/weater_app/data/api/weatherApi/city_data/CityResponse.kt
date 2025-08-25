package com.weater_app.weater_app.data.api.weatherApi.city_data

data class CityResponse(
    val name: String,
    val country: String,
    val state: String? = null,
    val lat: Double,
    val lon: Double
)