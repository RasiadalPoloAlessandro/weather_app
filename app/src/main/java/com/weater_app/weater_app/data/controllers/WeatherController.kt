package com.weater_app.weater_app.data.controllers

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weater_app.weater_app.R
import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.RetrofitInstance
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData
import com.weater_app.weater_app.data.cache.GetWeatherCase
import com.weater_app.weater_app.data.models.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class WeatherController(private val getWeatherUseCase: GetWeatherCase) : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi

    private val _weatherResult =
        MutableStateFlow<NetWorkResponse<WeatherData>>(NetWorkResponse.Loading)
    val weatherResult: StateFlow<NetWorkResponse<WeatherData>> = _weatherResult.asStateFlow()

    //Ui State (only for position)
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()


/*
* Function to get weather information of a city by passing only the city name
* */
    fun getWeather(city: String) {
        viewModelScope.launch {
            _weatherResult.value = NetWorkResponse.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = getWeatherUseCase.execute(city)

            when (result) {
                is NetWorkResponse.success -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weatherData = result.data,
                        errorMessage = null,
                        isDataFromCache = true
                    )
                }
                is NetWorkResponse.Error -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        weatherData = null
                    )
                }
                is NetWorkResponse.Loading -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }
    /*
    * Function to get weather information of user's current position
    * */
    fun getWeatherByCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weatherResult.value = NetWorkResponse.Loading
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = getWeatherUseCase.executeByCoord(latitude, longitude)

            when (result) {
                is NetWorkResponse.success -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        weatherData = result.data,
                        errorMessage = null,
                        isDataFromCache = true
                    )
                }
                is NetWorkResponse.Error -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message,
                        weatherData = null
                    )
                }
                is NetWorkResponse.Loading -> {
                    _weatherResult.value = result
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    /*
    Functions for handling multiple weathers
    * */
    fun getWeatherDataByIndex(index: Int): WeatherData? {
        return getWeatherUseCase.getWeatherDataByIndex(index)
    }

    fun getTotalPages(): Int {

        return getWeatherUseCase.getTotalCachedCities() + 1
    }

    fun getCitiesCount(): Int {
        return getWeatherUseCase.getTotalCachedCities()
    }

    fun hasCities(): Boolean {
        return getCitiesCount() > 0
    }

    @SuppressLint("SimpleDateFormat")
    fun getWeatherAnimation(weatherDescription: String): Int{
        val currentTime = Calendar.getInstance()
        val timeToMatch = Calendar.getInstance()

        timeToMatch[Calendar.HOUR_OF_DAY] = 18
        timeToMatch[Calendar.MINUTE] = 0

        if(currentTime > timeToMatch)
            Log.e("Time to match: ", "time matched")
        else
            Log.e("Time to match: ", "time not  ${currentTime.time}  ${timeToMatch.time}")

        return when{

            //Morning
            weatherDescription.contains("limpido", ignoreCase = true) && currentTime < timeToMatch ->  R.raw.sunny

            (currentTime < timeToMatch &&
                    (weatherDescription.contains("nuvoloso", ignoreCase = true) ||
                            weatherDescription.contains("nubi sparse", ignoreCase = true) ||
                            weatherDescription.contains("nuvole", ignoreCase = true) ||
                            weatherDescription.contains("coperto", ignoreCase = true))) -> R.raw.cloudy

            currentTime < timeToMatch && weatherDescription.contains("nuvoloso", ignoreCase = true) -> R.raw.cloudy
            currentTime < timeToMatch && (weatherDescription.contains("pioggia", ignoreCase = true) ||
                    weatherDescription.contains("temporali", ignoreCase = true)) -> R.raw.rainy

            //Night
            weatherDescription.contains("limpido", ignoreCase = true) && currentTime > timeToMatch ->  R.raw.night

            (currentTime > timeToMatch &&
                    (weatherDescription.contains("nuvoloso", ignoreCase = true) ||
                            weatherDescription.contains("nubi sparse", ignoreCase = true) ||
                            weatherDescription.contains("nuvole", ignoreCase = true) ||
                            weatherDescription.contains("coperto", ignoreCase = true))) -> R.raw.cloudy_night

            currentTime > timeToMatch && (weatherDescription.contains("pioggia", ignoreCase = true) ||
                    weatherDescription.contains("temporali", ignoreCase = true)) -> R.raw.rainy_night

            else -> R.raw.sunny
        }
    }
}