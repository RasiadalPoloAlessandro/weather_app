package com.weater_app.weater_app.data.cache

import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherData
import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherModel

class WeatherCacheManager {

    private val cache = mutableMapOf<String, WeatherData>()
    private val cacheExpiryTime = 5 * 60 * 1000L

    fun getCachedWeather(city: String): WeatherData? {
        val cachedData = cache[city]
        return if (cachedData != null && !isExpired(cachedData)) {
            cachedData
        } else {
            cache.remove(city)
            null
        }
    }

    fun cacheWeather(city: String, data: WeatherData) {
        cache[city] = data
    }

    private fun isExpired(data: WeatherData): Boolean {
        return System.currentTimeMillis() - data.timestamp > cacheExpiryTime
    }

    fun clearCache() {
        cache.clear()
    }
}