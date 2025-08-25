package com.weater_app.weater_app.data.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.RetrofitInstance
import com.weater_app.weater_app.data.api.weatherApi.city_data.CityResponse
import com.weater_app.weater_app.data.cache.GetCity
import com.weater_app.weater_app.data.models.CityUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityController(private val getCity: GetCity) : ViewModel() {

    private val _cityResult =
        MutableStateFlow<NetWorkResponse<List<CityResponse>>>(NetWorkResponse.Loading)

    private val _uiState = MutableStateFlow(CityUiState())

    val uiState: StateFlow<CityUiState> = _uiState.asStateFlow()

    fun getCityInformation(city: String) {
        viewModelScope.launch {
            _cityResult.value = NetWorkResponse.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = getCity.getCities(city)

            when (result) {
                is NetWorkResponse.success -> {
                    _cityResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        cityData = result.data,
                        errorMessage = null
                    )
                }

                is NetWorkResponse.Error -> {
                    _cityResult.value = result
                    _uiState.value = _uiState.value.copy( // Mancava l'assegnazione!
                        isLoading = false,
                        errorMessage = result.message,
                        cityData = null
                    )
                }

                is NetWorkResponse.Loading -> {
                    _cityResult.value = result
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }
}