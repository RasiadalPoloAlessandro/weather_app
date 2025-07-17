package com.weater_app.weater_app.data.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.RetrofitInstance
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData
import com.weater_app.weater_app.data.cache.GetWeatherCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val getWeatherUseCase: GetWeatherCase) : ViewModel() {


    private val weatherApi = RetrofitInstance.weatherApi

    private val _weatherResult = MutableStateFlow<NetWorkResponse<WeatherData>>(NetWorkResponse.Loading)
    val weatherResult: StateFlow<NetWorkResponse<WeatherData>> = _weatherResult.asStateFlow()

    // Stato per l'UI
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun getWeather(city: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _weatherResult.value = NetWorkResponse.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)

            if (forceRefresh) {
                // Se vogliamo forzare il refresh, possiamo implementare questa logica nel UseCase
            }

            val result = getWeatherUseCase.execute(city)

            when (result) {
                is NetWorkResponse.success -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weatherData = result.data,
                        errorMessage = null,
                        isDataFromCache = true // Potresti aggiungere questa info nel UseCase
                    )
                }
                is NetWorkResponse.Error -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        weatherData =  null
                    )
                }
                is NetWorkResponse.Loading -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }


    }

    fun getWeatherByCoordinates(latitude: Double, longitude: Double, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _weatherResult.value = NetWorkResponse.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)

            if (forceRefresh) {
                // Se vogliamo forzare il refresh, possiamo implementare questa logica nel UseCase
            }

            val result = getWeatherUseCase.executeByCoord(latitude, longitude)

            when (result) {
                is NetWorkResponse.success -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weatherData = result.data,
                        errorMessage = null,
                        isDataFromCache = true // Potresti aggiungere questa info nel UseCase
                    )
                }
                is NetWorkResponse.Error -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        weatherData =  null
                    )
                }
                is NetWorkResponse.Loading -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }
}