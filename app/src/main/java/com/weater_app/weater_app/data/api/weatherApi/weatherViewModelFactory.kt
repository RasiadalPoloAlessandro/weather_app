package com.weater_app.weater_app.data.api.weatherApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weater_app.weater_app.data.cache.GetWeatherCase
import com.weater_app.weater_app.data.models.WeatherViewModel

class WeatherViewModelFactory(
    private val getWeatherUseCase: GetWeatherCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(getWeatherUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}