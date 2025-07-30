package com.weater_app.weater_app.data.cache

import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.WeatherApi
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherPoint

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
            val response = weatherApi.getCurrentWeather(city)

            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    // Verifica che ci siano dati nella lista
                    if (apiResponse.list.isNotEmpty()) {
                        val currentWeatherData = apiResponse.list.first()
                        // Converti la risposta API in WeatherModel
                        val weatherData = WeatherData(
                            city = apiResponse.city.name,
                            temperature = currentWeatherData.main.temp.toString(),
                            description = currentWeatherData.weather.firstOrNull()?.description ?: "N/A",
                            humidity = currentWeatherData.main.humidity.toString(),
                            windSpeed = currentWeatherData.wind.speed.toString(),
                            pressure = currentWeatherData.main.pressure.toString(),
                            visibility = currentWeatherData.visibility.toString(),
                            temperatures = apiResponse.list.map { item ->
                                WeatherPoint(
                                    item.main.temp.toFloat(),
                                    item.dt_txt,
                                )
                            },
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

    suspend fun executeByCoord(latitude: Double, longitude: Double): NetWorkResponse<WeatherData> {
        return try {

            val response = weatherApi.getCurrentWeatherByCoord(latitude, longitude)

            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    // Verifica che ci siano dati nella lista
                    if (apiResponse.list.isNotEmpty()) {
                        val currentWeather = apiResponse //List
                        val currentWeatherData = currentWeather.list.first()

                        // Converti la risposta API in WeatherModel
                        val weatherData = WeatherData(
                            city = apiResponse.city.name,
                            temperature = currentWeatherData.main.temp.toString(),
                            description = currentWeatherData.weather.firstOrNull()?.description ?: "N/A",
                            humidity = currentWeatherData.main.humidity.toString(),
                            windSpeed = currentWeatherData.wind.speed.toString(),
                            pressure = currentWeatherData.main.pressure.toString(),
                            visibility = currentWeatherData.visibility.toString(),
                            temperatures = currentWeather.list.take(10).map {
                                item -> WeatherPoint(
                                    item.main.temp.toFloat(),
                                    item.dt_txt,
                                )
                            },
                            country = apiResponse.city.country
                        )

                        // Salva in cache
                        cacheManager.cacheWeather(weatherData.city, weatherData)

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