package com.weater_app.weater_app.data.cache

import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData

class WeatherCacheManager {

    private val cache = mutableMapOf<String, WeatherData>()
    private val weatherList = mutableListOf<WeatherData>() // Lista di tutti i meteo
    private val cacheExpiryTime = 5 * 60 * 1000L

    fun getCachedWeather(city: String): WeatherData? {
        val cachedData = cache[city]
        return if (cachedData != null && !isExpired(cachedData)) {
            cachedData
        } else {
            cache.remove(city)
            weatherList.removeAll { it.city.equals(city, ignoreCase = true) && isExpired(it) }
            null
        }
    }

    fun cacheWeather(city: String, data: WeatherData) {
        cache[city] = data

        val existingIndex = weatherList.indexOfFirst {
            it.city.equals(city, ignoreCase = true)
        }

        if (existingIndex != -1) {
            weatherList[existingIndex] = data
        } else {
            weatherList.add(data)
        }
    }

    private fun isExpired(data: WeatherData): Boolean {
        return System.currentTimeMillis() - data.timestamp > cacheExpiryTime
    }

    // Handle more weathers

    fun getAllWeatherData(): List<WeatherData> {
        val validWeatherData = weatherList.filter { !isExpired(it) }


        weatherList.clear()
        weatherList.addAll(validWeatherData)

        return validWeatherData.toList()
    }

    fun getWeatherDataCount(): Int {
        return getAllWeatherData().size
    }

    fun getWeatherDataByIndex(index: Int): WeatherData? {
        val validData = getAllWeatherData()
        return if (index in validData.indices) validData[index] else null
    }

    fun removeWeatherData(cityName: String) {
        cache.remove(cityName)
        weatherList.removeAll { it.city.equals(cityName, ignoreCase = true) }
    }

    fun hasWeatherData(cityName: String): Boolean {
        return getAllWeatherData().any { it.city.equals(cityName, ignoreCase = true) }
    }

    fun clearCache() {
        cache.clear()
        weatherList.clear()
    }
}