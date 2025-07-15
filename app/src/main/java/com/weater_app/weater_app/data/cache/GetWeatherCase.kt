package com.weater_app.weater_app.data.cache

import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.Constant
import com.weater_app.weater_app.data.api.weatherApi.WeatherApi
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherModel

class GetWeatherCase(
    private val weatherApi: WeatherApi,
    private val cacheManager: WeatherCacheManager
) {

    suspend fun execute(city: String): NetWorkResponse<WeatherData> {
        return try {
            // Prima controlla la cache
            val cachedData = cacheManager.getCachedWeather(city)
            if (cachedData != null) {
                return NetWorkResponse.success(cachedData)
            }

            // Se non c'è cache valida, chiama l'API
            val response = weatherApi.getCurrentWeather(city, Constant.apikey)

            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    // Verifica che ci siano dati nella lista
                    if (apiResponse.list.isNotEmpty()) {
                        val currentWeather = apiResponse.list.first() // Prendi il primo elemento (tempo attuale)

                        // Converti la risposta API in WeatherModel
                        val weatherData = WeatherData(
                            city = apiResponse.city.name,
                            temperature = currentWeather.main.temp.toString(),
                            description = currentWeather.weather.firstOrNull()?.description ?: "N/A",
                            humidity = currentWeather.main.humidity.toString(),
                            windSpeed = currentWeather.wind.speed.toString(),
                            country = apiResponse.city.country
                        )

                        // Salva in cache
                        cacheManager.cacheWeather(city, weatherData)

                        NetWorkResponse.success(weatherData)
                    } else {
                        NetWorkResponse.Error("No weather data available")
                    }
                } ?: NetWorkResponse.Error("Empty response body")
            } else {
                NetWorkResponse.Error("API Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            NetWorkResponse.Error("Network error: ${e.message}")
        }
    }
}