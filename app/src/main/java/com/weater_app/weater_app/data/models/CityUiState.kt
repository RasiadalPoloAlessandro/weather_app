package com.weater_app.weater_app.data.models

import com.weater_app.weater_app.data.api.weatherApi.city_data.CityResponse

data class CityUiState(
    val isLoading: Boolean = false,
    val cityData: List<CityResponse>? = null,
    val errorMessage: String? = null,
)