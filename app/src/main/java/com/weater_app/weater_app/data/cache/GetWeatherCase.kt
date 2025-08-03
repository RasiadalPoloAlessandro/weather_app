package com.weater_app.weater_app.data.cache

import com.weater_app.weater_app.data.api.NetWorkResponse
import com.weater_app.weater_app.data.api.weatherApi.WeatherApi
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherPoint

class GetWeatherCase(
    private val weatherApi: WeatherApi,
    val cacheManager: WeatherCacheManager // Reso pubblico per l'accesso dal ViewModel
) {

    suspend fun execute(city: String): NetWorkResponse<WeatherData> {
        return try {
            // Check the cache
            val cachedData = cacheManager.getCachedWeather(city)
            if (cachedData != null) {
                return NetWorkResponse.success(cachedData)
            }

            // Call the API if the cache is empty
            val response = weatherApi.getCurrentWeather(city)

            if (response.isSuccessful) {
                response.body()?.let { apiResponse ->
                    //Check if there is data
                    if (apiResponse.list.isNotEmpty()) {
                        val currentWeatherData = apiResponse.list.first()
                        // Collect all the data you need
                        val weatherData = WeatherData(
                            city = apiResponse.city.name,
                            temperature = currentWeatherData.main.temp.toInt().toString(),
                            description = currentWeatherData.weather.firstOrNull()?.description ?: "N/A",
                            humidity = currentWeatherData.main.humidity.toString(),
                            windSpeed = currentWeatherData.wind.speed.toString(),
                            pressure = currentWeatherData.main.pressure.toString(),
                            visibility = currentWeatherData.visibility.toString(),
                            temperatures = apiResponse.list.take(10).map { item ->
                                WeatherPoint(
                                    item.main.temp.toInt().toFloat(),
                                    item.dt_txt,
                                )
                            },
                            country = apiResponse.city.country
                        )

                        // Save in cache
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
                    // Check if there is data
                    if (apiResponse.list.isNotEmpty()) {
                        val currentWeather = apiResponse //List
                        val currentWeatherData = currentWeather.list.first()

                        val weatherData = WeatherData(
                            city = apiResponse.city.name,
                            temperature = currentWeatherData.main.temp.toInt().toString(),
                            description = currentWeatherData.weather.firstOrNull()?.description ?: "N/A",
                            humidity = currentWeatherData.main.humidity.toString(),
                            windSpeed = currentWeatherData.wind.speed.toString(),
                            pressure = currentWeatherData.main.pressure.toString(),
                            visibility = currentWeatherData.visibility.toString(),
                            temperatures = currentWeather.list.take(10).map {
                                    item -> WeatherPoint(
                                item.main.temp.toInt().toFloat(),
                                item.dt_txt,
                            )
                            },
                            country = apiResponse.city.country
                        )

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

    // Handle Multiple cities

    suspend fun addCityByName(cityName: String): NetWorkResponse<WeatherData> {
        if (cacheManager.hasWeatherData(cityName)) {
            val cachedData = cacheManager.getCachedWeather(cityName)
            if (cachedData != null) {
                return NetWorkResponse.success(cachedData)
            }
        }

        return execute(cityName)
    }

    suspend fun addCityByCoordinates(latitude: Double, longitude: Double): NetWorkResponse<WeatherData> {
        return executeByCoord(latitude, longitude)
    }

    fun removeCityFromCache(cityName: String) {
        cacheManager.removeWeatherData(cityName)
    }

    fun getAllCachedWeatherData(): List<WeatherData> {
        return cacheManager.getAllWeatherData()
    }

    fun getWeatherDataByIndex(index: Int): WeatherData? {
        return cacheManager.getWeatherDataByIndex(index)
    }

    fun getTotalCachedCities(): Int {
        return cacheManager.getWeatherDataCount()
    }

    suspend fun refreshCity(cityName: String): NetWorkResponse<WeatherData> {
        return execute(cityName)
    }

    suspend fun refreshAllCities(): List<NetWorkResponse<WeatherData>> {
        val results = mutableListOf<NetWorkResponse<WeatherData>>()
        val allCities = cacheManager.getAllWeatherData()

        allCities.forEach { weatherData ->
            val result = execute(weatherData.city)
            results.add(result)
        }

        return results
    }
}