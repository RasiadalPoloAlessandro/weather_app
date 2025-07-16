package com.weater_app.weater_app.data.models

import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val errorMessage: String? = null,
    val isDataFromCache: Boolean = false
)