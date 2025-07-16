package com.weater_app.weater_app.data.api.weatherApi

import com.weater_app.weater_app.data.api.weatherApi.weather_data.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
    ): Response<WeatherModel>
}