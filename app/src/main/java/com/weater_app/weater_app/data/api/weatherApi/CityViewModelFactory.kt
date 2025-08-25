package com.weater_app.weater_app.data.api.weatherApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weater_app.weater_app.data.cache.GetCity
import com.weater_app.weater_app.data.controllers.CityController

class CityViewModelFactory(
    private val getCity: GetCity
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityController::class.java)) {
            return CityController(getCity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}